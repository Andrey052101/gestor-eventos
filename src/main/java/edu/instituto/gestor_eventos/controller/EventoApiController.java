package edu.instituto.gestor_eventos.controller;

import edu.instituto.gestor_eventos.dto.EventoRequest;
import edu.instituto.gestor_eventos.dto.EventoResponse;
import edu.instituto.gestor_eventos.service.EventoApiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller: solo define los endpoints y arma las respuestas HTTP.
 * La lógica y las validaciones están en EventoApiService.
 */
@RestController
@RequestMapping("/api/eventos")
public class EventoApiController {

    private final EventoApiService servicio;

    public EventoApiController(EventoApiService servicio) {
        this.servicio = servicio;
    }

    // GET /api/eventos -> 200
    @GetMapping
    public ResponseEntity<List<EventoResponse>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }

    // GET /api/eventos/{id} -> 200 o 404
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtener(id));
    }

    // POST /api/eventos -> 201 + cabecera Location, o 400
    @PostMapping
    public ResponseEntity<EventoResponse> crear(@Valid @RequestBody EventoRequest datos) {
        EventoResponse creado = servicio.crear(datos);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(ubicacion).body(creado);
    }

    // PUT /api/eventos/{id} -> 200, 400 o 404
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody EventoRequest datos) {
        return ResponseEntity.ok(servicio.actualizar(id, datos));
    }

    // DELETE /api/eventos/{id} -> 204 (sin contenido) o 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}