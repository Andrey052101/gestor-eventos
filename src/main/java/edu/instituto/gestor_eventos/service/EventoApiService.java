package edu.instituto.gestor_eventos.service;

import edu.instituto.gestor_eventos.dto.EventoRequest;
import edu.instituto.gestor_eventos.dto.EventoResponse;
import edu.instituto.gestor_eventos.exception.DatosInvalidosException;
import edu.instituto.gestor_eventos.exception.RecursoNoEncontradoException;
import edu.instituto.gestor_eventos.model.Categoria;
import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.repository.EventoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de la API REST: contiene las operaciones y las validaciones de negocio.
 * Usa la misma persistencia de la Semana 13 (EventoRepository / MySQL).
 * El EventoService original (el de las páginas web) no se modifica.
 */
@Service
public class EventoApiService {

    private final EventoRepository eventoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public EventoApiService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Transactional(readOnly = true)
    public List<EventoResponse> listar() {
        return eventoRepository.findAll().stream().map(this::aRespuesta).toList();
    }

    @Transactional(readOnly = true)
    public EventoResponse obtener(Long id) {
        return aRespuesta(buscar(id));
    }

    @Transactional
    public EventoResponse crear(EventoRequest datos) {
        Evento evento = new Evento();
        copiarDatos(evento, datos);
        return aRespuesta(eventoRepository.save(evento));
    }

    @Transactional
    public EventoResponse actualizar(Long id, EventoRequest datos) {
        Evento evento = buscar(id);
        copiarDatos(evento, datos);
        return aRespuesta(eventoRepository.save(evento));
    }

    @Transactional
    public void eliminar(Long id) {
        eventoRepository.delete(buscar(id));
    }

    // ---------- métodos de apoyo ----------

    private Evento buscar(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un evento con id " + id));
    }

    private void copiarDatos(Evento evento, EventoRequest datos) {
        Categoria categoria = entityManager.find(Categoria.class, datos.categoriaId());
        if (categoria == null) {
            throw new DatosInvalidosException("La categoría con id " + datos.categoriaId() + " no existe");
        }
        evento.setNombre(datos.nombre().strip());
        evento.setDescripcion(datos.descripcion().strip());
        evento.setFecha(datos.fecha().strip());
        evento.setLugar(datos.lugar().strip());
        evento.setCategoria(categoria);
    }

    private EventoResponse aRespuesta(Evento e) {
        Categoria c = e.getCategoria();
        return new EventoResponse(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getFecha(),
                e.getLugar(),
                c == null ? null : c.getId(),
                c == null ? null : c.getNombre());
    }
}