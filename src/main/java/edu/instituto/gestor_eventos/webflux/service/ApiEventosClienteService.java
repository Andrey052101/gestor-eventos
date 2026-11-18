package edu.instituto.gestor_eventos.webflux.service;

import edu.instituto.gestor_eventos.dto.EventoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ApiEventosClienteService {

    private final WebClient cliente;

    public ApiEventosClienteService(WebClient eventosWebClient) {
        this.cliente = eventosWebClient;
    }

    // Consulta la API REST de la semana 14 usando WebClient
    public Mono<String> consultarApi() {
        return cliente.get()
                .uri("/api/eventos")
                .retrieve()
                .bodyToMono(EventoResponse[].class)
                .timeout(Duration.ofSeconds(3))
                .map(lista -> "WebClient consulto /api/eventos y recibio " + lista.length + " eventos.")
                .onErrorResume(error -> Mono.just("API de eventos no disponible"));
    }
}