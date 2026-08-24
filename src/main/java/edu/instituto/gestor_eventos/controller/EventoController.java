package edu.instituto.gestor_eventos.controller;

import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.service.EventoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/saludo")
    public String saludo() {
        return "Proyecto Gestor de Eventos funcionando";
    }

    @GetMapping("/eventos")
    public List<Evento> listarEventos() {
        return eventoService.listar();
    }
}