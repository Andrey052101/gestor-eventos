package edu.instituto.gestor_eventos.dto;

/**
 * Modelo de SALIDA: lo que la API devuelve. Incluye el id del recurso.
 */
public record EventoResponse(
        Long id,
        String nombre,
        String descripcion,
        String fecha,
        String lugar,
        Long categoriaId,
        String categoriaNombre
) {
}