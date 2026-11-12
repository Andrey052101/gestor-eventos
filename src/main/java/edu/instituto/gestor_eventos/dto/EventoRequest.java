package edu.instituto.gestor_eventos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Modelo de ENTRADA: lo que el cliente envía en POST y PUT.
 * No lleva id: el id lo genera la base de datos (POST) o viene en la URL (PUT).
 */
public record EventoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        @NotBlank(message = "La fecha es obligatoria")
        @Pattern(regexp = "^$|\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe tener formato AAAA-MM-DD")
        String fecha,

        @NotBlank(message = "El lugar es obligatorio")
        String lugar,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId
) {
}