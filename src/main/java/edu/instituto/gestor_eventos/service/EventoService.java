package edu.instituto.gestor_eventos.service;

import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.model.EventoForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService {

    private List<Evento> eventos = new ArrayList<>(List.of(
            new Evento(1L, "FC Barcelona vs Real Madrid", "El Clasico de la Liga Espanola", "2026-09-15", "Camp Nou, Barcelona"),
            new Evento(2L, "Bad Bunny World Tour", "El conejo malo regresa a Bogota", "2026-09-20", "Bogota, Estadio Nemesio el Campin"),
            new Evento(3L, "Atletico vs FC Barcelona", "Jornada 3 de la Liga Espanola", "2026-09-27", "Metropolitano, Madrid"),
            new Evento(4L, "Fuck News", "Camilo Sanchez y Camilo Pardo regresan al Movistar Arena con Fuck News", "2026-09-05", "Movistar Arena, Bogota")
    ));

    public List<Evento> listar() {
        return eventos;
    }

    public void guardar(EventoForm form) {
        Long nuevoId = (long) (eventos.size() + 1);
        eventos.add(new Evento(
                nuevoId,
                form.getNombre(),
                form.getDescripcion(),
                form.getFecha(),
                form.getLugar()
        ));
    }
}