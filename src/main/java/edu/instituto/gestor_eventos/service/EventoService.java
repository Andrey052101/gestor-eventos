package edu.instituto.gestor_eventos.service;

import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.model.EventoForm;
import edu.instituto.gestor_eventos.repository.EventoRepository;
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

    public void guardar(EventoForm form) {
        Evento evento = new Evento();
        evento.setNombre(form.getNombre());
        evento.setDescripcion(form.getDescripcion());
        evento.setFecha(form.getFecha());
        evento.setLugar(form.getLugar());
        eventoRepository.save(evento);
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }

    public void actualizar(Long id, Evento eventoActualizado) {
        Evento evento = buscarPorId(id);
        evento.setNombre(eventoActualizado.getNombre());
        evento.setDescripcion(eventoActualizado.getDescripcion());
        evento.setFecha(eventoActualizado.getFecha());
        evento.setLugar(eventoActualizado.getLugar());
        eventoRepository.save(evento);
    }

    public void eliminar(Long id) {
        eventoRepository.deleteById(id);
    }
}