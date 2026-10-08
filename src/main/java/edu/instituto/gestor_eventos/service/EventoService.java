package edu.instituto.gestoreventos.service;

import edu.instituto.gestoreventos.model.Evento;
import edu.instituto.gestoreventos.repository.EventoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // Listar normal (sin paginación, por si se usa en otro lado)
    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    // Listar con Paginación
    public Page<Evento> listarPaginado(Pageable pageable) {
        return eventoRepository.findAll(pageable);
    }

    // Buscar por Nombre con Paginación
    public Page<Evento> buscarPorNombre(String nombre, Pageable pageable) {
        return eventoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    // Buscar por Categoría con Paginación
    public Page<Evento> buscarPorCategoria(Long categoriaId, Pageable pageable) {
        return eventoRepository.findByCategoriaId(categoriaId, pageable);
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    public Evento guardar(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void eliminar(Long id) {
        eventoRepository.deleteById(id);
    }
}