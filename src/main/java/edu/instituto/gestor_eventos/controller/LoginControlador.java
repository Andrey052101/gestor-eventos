package edu.instituto.gestor_eventos.controller;

import edu.instituto.gestor_eventos.model.Usuario;
import edu.instituto.gestor_eventos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginControlador {

    private final UsuarioService usuarioService;

    public LoginControlador(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String entrar(@RequestParam(defaultValue = "") String nombre,
                         @RequestParam(defaultValue = "") String contrasena,
                         HttpSession sesion,
                         RedirectAttributes mensajes) {

        if (nombre.isBlank() || contrasena.isBlank()) {
            mensajes.addFlashAttribute("error", "Todos los campos son obligatorios.");
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.login(nombre, contrasena);
        if (usuario == null) {
            mensajes.addFlashAttribute("error", "Usuario o contraseña incorrectos.");
            return "redirect:/login";
        }

        sesion.setAttribute("usuario", usuario.getNombre());
        sesion.setAttribute("rol", usuario.getRol());
        return "redirect:/eventos";
    }

    @PostMapping("/logout")
    public String salir(HttpSession sesion) {
        sesion.invalidate();
        return "redirect:/login";
    }
}