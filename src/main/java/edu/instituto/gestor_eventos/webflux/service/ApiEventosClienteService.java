package edu.instituto.gestor_eventos.webflux.service;

import edu.instituto.gestor_eventos.dto.EventoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ApiEventosClienteService {

    private final WebClient cliente;
    private final WebClient clienteCaido;

    public ApiEventosClienteService(WebClient eventosWebClient,
                                    WebClient.Builder builder,
                                    @Value("${demo.api.caida.url:http://localhost:9999}") String urlCaida) {
        this.cliente = eventosWebClient;
        this.clienteCaido = builder.baseUrl(urlCaida).build();
    }

    // Consulta la API REST de eventos (disponible)
    public Mono<String> consultarApi() {
        return consultar(cliente, "API de eventos");
    }

    // Misma consulta a un puerto sin servicio: demuestra timeout y onErrorResume
    public Mono<String> consultarFuenteCaida() {
        return consultar(clienteCaido, "Fuente externa");
    }

    private Mono<String> consultar(WebClient webClient, String fuente) {
        return webClient.get()
                .uri("/api/eventos")
                .retrieve()
                .bodyToMono(EventoResponse[].class)
                .timeout(Duration.ofSeconds(3))
                .map(lista -> "WebClient consulto /api/eventos (" + fuente + ") y recibio " + lista.length + " eventos.")
                .onErrorResume(error -> Mono.just(
                        "Fuente no disponible (" + fuente + "). Causa: " + error.getClass().getSimpleName()));
    }
}
