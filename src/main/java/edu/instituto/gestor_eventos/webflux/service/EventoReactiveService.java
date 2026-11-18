package edu.instituto.gestor_eventos.webflux.service;

import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.repository.EventoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

@Service
public class EventoReactiveService {

    private final EventoRepository eventoRepository;

    public EventoReactiveService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // Mono: un solo resultado. JPA es bloqueante, por eso se manda a boundedElastic.
    public Mono<String> bienvenida(String usuario) {
        String nombre = (usuario == null || usuario.isBlank()) ? "visitante" : usuario.trim();
        return Mono.fromCallable(() -> eventoRepository.count())
                .subscribeOn(Schedulers.boundedElastic())
                .map(total -> "Bienvenido a Gestor de Eventos, " + nombre
                        + ". Hay " + total + " eventos registrados.");
    }

    // Flux: los 5 eventos mas recientes, uno por segundo
    public Flux<String> eventosRecientes() {
        Mono<List<Evento>> consulta = Mono
                .fromCallable(() -> eventoRepository.findAll(Sort.by(Sort.Direction.DESC, "fecha")))
                .subscribeOn(Schedulers.boundedElastic());

        return consulta
                .flatMapMany(Flux::fromIterable)
                .take(5)
                .delayElements(Duration.ofSeconds(1))
                .map(this::armarTexto);
    }

    private String armarTexto(Evento e) {
        String categoria = (e.getCategoria() == null) ? "Sin categoria" : e.getCategoria().getNombre();
        return "Evento: " + e.getNombre() + " | " + e.getFecha()
                + " | " + e.getLugar() + " | " + categoria;
    }
}