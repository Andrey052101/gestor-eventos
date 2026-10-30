package edu.instituto.gestor_eventos.controller;

import edu.instituto.gestor_eventos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistroControlador {

    private final UsuarioService usuarioService;

    public RegistroControlador(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam(defaultValue = "") String nombre,
                            @RequestParam(defaultValue = "") String correo,
                            @RequestParam(defaultValue = "") String contrasena,
                            RedirectAttributes mensajes) {

        String error = usuarioService.registrar(nombre, correo, contrasena);
        if (error != null) {
            mensajes.addFlashAttribute("error", error);
            return "redirect:/registro";
        }

        mensajes.addFlashAttribute("ok", "¡Cuenta creada! Ya puedes iniciar sesión.");
        return "redirect:/login";
    }} //Maneja el registro de usuarios (GET muestra el form, POST crea el usuario nuevo)