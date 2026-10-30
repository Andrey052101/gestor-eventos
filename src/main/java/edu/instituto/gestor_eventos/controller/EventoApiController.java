package edu.instituto.gestor_eventos.controller;

import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
public class EventoApiController {

    private final EventoService eventoService;

    public EventoApiController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // GET /api/eventos
    @GetMapping
    public ResponseEntity<List<Evento>> listar() {
        List<Evento> eventos = eventoService.listarPaginado(Pageable.unpaged()).getContent();
        return ResponseEntity.ok(eventos);
    }

    // GET /api/eventos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Evento evento = eventoService.buscarPorId(id);
        if (evento == null) return noEncontrado(id);
        return ResponseEntity.ok(evento);
    }

    // POST /api/eventos
    @PostMapping
    public ResponseEntity<Evento> crear(@Valid @RequestBody Evento evento) {
        evento.setId(null);
        return ResponseEntity.ok(eventoService.guardar(evento));
    }

    // PUT /api/eventos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody Evento datos) {
        if (eventoService.buscarPorId(id) == null) return noEncontrado(id);
        datos.setId(id);
        return ResponseEntity.ok(eventoService.guardar(datos));
    }

    // DELETE /api/eventos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (eventoService.buscarPorId(id) == null) return noEncontrado(id);
        eventoService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Evento " + id + " eliminado"));
    }

    private ResponseEntity<Map<String, String>> noEncontrado(Long id) {
        return ResponseEntity.status(404)
                .body(Map.of("error", "No existe un evento con id " + id));
    }
}