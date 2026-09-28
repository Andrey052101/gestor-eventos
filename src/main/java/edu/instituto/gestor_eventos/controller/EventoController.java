package edu.instituto.gestor_eventos.controller;

import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.model.EventoForm;
import edu.instituto.gestor_eventos.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/eventos")
    public String listarEventos(Model model) {
        model.addAttribute("eventos", eventoService.listar());
        return "eventos";
    }

    @GetMapping("/eventos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("eventoForm", new EventoForm());
        return "nuevo-evento";
    }

    @PostMapping("/eventos/nuevo")
    public String guardarEvento(@Valid @ModelAttribute("eventoForm") EventoForm form,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-evento";
        }
        eventoService.guardar(form);
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/editar/{id}")
    public String mostrarEditar(@PathVariable Long id, Model model) {
        Evento evento = eventoService.buscarPorId(id);
        model.addAttribute("evento", evento);
        return "editar-evento";
    }

    @PostMapping("/eventos/editar/{id}")
    public String actualizarEvento(@PathVariable Long id,
                                   @Valid @ModelAttribute("evento") Evento evento,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "editar-evento";
        }
        eventoService.actualizar(id, evento);
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventoService.eliminar(id);
        return "redirect:/eventos";
    }
}