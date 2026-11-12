package edu.instituto.gestor_eventos.reactivo;

import edu.instituto.gestor_eventos.dto.EventoResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventoReactivoService {

    // Datos de ejemplo (no se guardan en la base de datos).
    // Vigente = fecha de hoy o futura. El evento 4 ya paso: es el inactivo.
    public Flux<EventoResponse> fuente() {
        LocalDate hoy = LocalDate.now();
        return Flux.fromIterable(List.of(
                evento(1, "Taller de datos", "Auditorio principal", hoy.plusDays(9).toString(), 1, "Academico"),
                evento(2, "Charla de ciberseguridad", "Sala de sistemas", hoy.plusDays(11).toString(), 1, "Academico"),
                evento(3, "Feria de proyectos", "Auditorio principal", hoy.plusDays(20).toString(), 1, "Academico"),
                evento(4, "Torneo de robotica", "Gimnasio", hoy.minusDays(27).toString(), 2, "Deportivo"),
                evento(5, "Jornada de bienvenida", "Patio central", hoy.plusDays(5).toString(), 3, "Institucional")
        ));
    }

    // filter: solo eventos vigentes
    public Flux<EventoResponse> eventosVigentes() {
        return fuente().filter(this::esVigente);
    }

    // filter + map: cada evento se convierte en un texto
    public Flux<String> procesarVigentes() {
        return eventosVigentes()
                .map(e -> e.nombre() + " [" + e.categoriaNombre() + "] en " + e.lugar() + " (" + e.fecha() + ")");
    }

    public Flux<String> procesarVigentesEnLugar(String lugar) {
        return eventosVigentes()
                .filter(e -> e.lugar().equalsIgnoreCase(lugar))
                .map(e -> e.nombre() + " (" + e.fecha() + ")");
    }

    // count: un solo numero dentro de un Mono
    public Mono<Long> contarVigentes() {
        return eventosVigentes().count();
    }

    public Mono<Long> contarVigentesEnLugar(String lugar) {
        return eventosVigentes()
                .filter(e -> e.lugar().equalsIgnoreCase(lugar))
                .count();
    }

    // Mono: un resultado unico (queda vacio si el id no existe)
    public Mono<EventoResponse> buscarPorId(long id) {
        return fuente().filter(e -> e.id().equals(id)).next();
    }

    // Error real: un evento con la fecha mal escrita hace fallar LocalDate.parse
    public Flux<String> procesarConFechaInvalida() {
        EventoResponse malo = evento(99, "Evento con fecha mal escrita", "Sin lugar", "pronto", 1, "Academico");
        return Flux.concat(fuente(), Flux.just(malo))
                .filter(this::esVigente)
                .map(e -> e.nombre() + " (" + e.fecha() + ")");
    }

    private boolean esVigente(EventoResponse e) {
        return !LocalDate.parse(e.fecha()).isBefore(LocalDate.now());
    }

    private EventoResponse evento(long id, String nombre, String lugar, String fecha,
                                  long categoriaId, String categoria) {
        return new EventoResponse(id, nombre, "Evento de ejemplo", fecha, lugar, categoriaId, categoria);
    }
}