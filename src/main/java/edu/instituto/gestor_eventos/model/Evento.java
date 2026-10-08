package edu.instituto.gestoreventos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del evento es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "El lugar es obligatorio")
    private String lugar;

    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Evento() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}