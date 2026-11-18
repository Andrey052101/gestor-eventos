package edu.instituto.gestor_eventos.webflux.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient eventosWebClient(WebClient.Builder builder,
                                      @Value("${demo.api.url}") String urlBase) {
        return builder.baseUrl(urlBase).build();
    }
}