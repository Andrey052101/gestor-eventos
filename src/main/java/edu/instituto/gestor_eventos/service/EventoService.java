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
}