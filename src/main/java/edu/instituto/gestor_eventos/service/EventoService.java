package edu.instituto.gestor_eventos.service;

import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.repository.EventoRepository;
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

    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    public Page<Evento> listarPaginado(Pageable pageable) {
        return eventoRepository.findAll(pageable);
    }

    public Page<Evento> buscarPorNombre(String nombre, Pageable pageable) {
        return eventoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

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