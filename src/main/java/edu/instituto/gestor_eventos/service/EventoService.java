package edu.instituto.gestor_eventos.service;

import edu.instituto.gestor_eventos.model.Evento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    public List<Evento> listar() {
        return List.of(
                new Evento(1L, "FC Barcelona vs Real Madrid", "El Clásico de la Liga Española", "2026-09-15", "Camp Nou, Barcelona"),
                new Evento(2L, "Bad Bunny World Tour", "El conejo malo regresa a España", "2026-09-20", "Palau Sant Jordi, Barcelona"),
                new Evento(3L, "Atlético vs FC Barcelona", "Jornada 3 de la Liga Española", "2026-09-27", "Metropolitano, Madrid"),
                new Evento(4L, "Coldplay Music of the Spheres", "Noche mágica en el Olímpico", "2026-09-05", "Estadio Olímpico, Barcelona")
        );
    }
}