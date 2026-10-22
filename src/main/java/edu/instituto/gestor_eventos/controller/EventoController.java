package edu.instituto.gestor_eventos.controller;

import edu.instituto.gestor_eventos.model.Evento;
import edu.instituto.gestor_eventos.service.CategoriaService;
import edu.instituto.gestor_eventos.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;
    private final CategoriaService categoriaService;

    public EventoController(EventoService eventoService, CategoriaService categoriaService) {
        this.eventoService = eventoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) Long categoriaId,
            Model model) {

        Pageable pageable = PageRequest.of(page, 6);
        Page<Evento> paginaEventos;

        if (buscar != null && !buscar.trim().isEmpty()) {
            paginaEventos = eventoService.buscarPorNombre(buscar, pageable);
        } else if (categoriaId != null) {
            paginaEventos = eventoService.buscarPorCategoria(categoriaId, pageable);
        } else {
            paginaEventos = eventoService.listarPaginado(pageable);
        }

        model.addAttribute("eventos", paginaEventos.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginaEventos.getTotalPages());
        model.addAttribute("buscar", buscar);
        model.addAttribute("categoriaId", categoriaId);
        model.addAttribute("categorias", categoriaService.listar());
        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("evento", new Evento());
        model.addAttribute("categorias", categoriaService.listar());
        return "nuevo-evento";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("evento", eventoService.buscarPorId(id));
        model.addAttribute("categorias", categoriaService.listar());
        return "editar-evento";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("evento") Evento evento,
                          BindingResult result,
                          Model model,
                          RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listar());
            return evento.getId() == null ? "nuevo-evento" : "editar-evento";
        }
        eventoService.guardar(evento);
        ra.addFlashAttribute("mensajeExito", "¡Evento guardado con éxito!");
        return "redirect:/eventos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        eventoService.eliminar(id);
        ra.addFlashAttribute("mensajeExito", "Evento eliminado correctamente.");
        return "redirect:/eventos";
    }
}