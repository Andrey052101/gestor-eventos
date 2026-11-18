package edu.instituto.gestor_eventos.webflux.controller;

import edu.instituto.gestor_eventos.webflux.service.ApiEventosClienteService;
import edu.instituto.gestor_eventos.webflux.service.EventoReactiveService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reactive")
public class ReactiveEventoController {

    private final EventoReactiveService servicio;
    private final ApiEventosClienteService cliente;

    public ReactiveEventoController(EventoReactiveService servicio,
                                    ApiEventosClienteService cliente) {
        this.servicio = servicio;
        this.cliente = cliente;
    }

    @GetMapping("/bienvenida/{usuario}")
    public Mono<String> bienvenida(@PathVariable String usuario) {
        return servicio.bienvenida(usuario);
    }

    @GetMapping(value = "/eventos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> eventos() {
        return servicio.eventosRecientes();
    }

    @GetMapping("/estado-api")
    public Mono<String> estadoApi() {
        return cliente.consultarApi();
    }

    @GetMapping("/estado-api-caida")
    public Mono<String> estadoApiCaida() {
        return cliente.consultarFuenteCaida();
    }
}
