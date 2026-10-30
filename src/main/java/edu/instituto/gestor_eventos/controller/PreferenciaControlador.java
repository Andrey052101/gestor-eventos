package edu.instituto.gestor_eventos.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//Color interfaz del usuario(guarda en cookie)
@Controller
public class PreferenciaControlador {
    @PostMapping("/preferencia")
    public String guardarTema(@RequestParam String tema,
                              HttpServletResponse respuesta,
                              HttpServletRequest solicitud,
                              RedirectAttributes mensajes) {


//Crea la cookie con la eleccion (claro u oscuro)
        Cookie cookieTema = new Cookie("tema", tema);
        cookieTema.setMaxAge(60 * 60 * 24 * 30); // La sesion dura 30 días en el navegador
        cookieTema.setPath("/"); // Valida
        respuesta.addCookie(cookieTema);


        mensajes.addFlashAttribute("ok", "Tema guardado: " + tema);
        return "redirect:/eventos";

    }} //Maneja la preferencia (GET lee la cookie de tema, POST guarda la cookie,