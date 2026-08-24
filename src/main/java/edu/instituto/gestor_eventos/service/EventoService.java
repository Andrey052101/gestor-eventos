package edu.instituto.gestor_eventos.service;

import edu.instituto.gestor_eventos.model.Evento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    public List<Evento> listar() {

        return List.of(
                new Evento(
                        1L,
                        "Feria Tecnológica",
                        "Exposición de proyectos tecnológicos",
                        "2026-09-15",
                        "Auditorio Principal"
                ),

                new Evento(
                        2L,
                        "Conferencia Spring Boot",
                        "Introducción al desarrollo con Spring Boot",
                        "2026-09-20",
                        "Sala 2"
                )
        );
    }
}