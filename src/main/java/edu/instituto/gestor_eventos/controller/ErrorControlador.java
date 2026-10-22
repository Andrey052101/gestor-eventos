package edu.instituto.gestor_eventos.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//Captura errores HTTP y muestra la pagina
@Controller
public class ErrorControlador implements ErrorController {
// Esta ruta la llama Spring Boot automáticamente cuando hay un error
@RequestMapping("/error")
public String manejarError(HttpServletRequest solicitud, Model modelo) {

    // Lee el codigo de error
    Integer codigoError = (Integer) solicitud.getAttribute("jakarta.servlet.error.status_code");

    if (codigoError == null) {
        codigoError = 500;
    }

    // Prepara el mensaje según el tipo de error
    String mensaje;
    if (codigoError == 404) {
        mensaje = "La página que buscas no existe.";
    } else if (codigoError == 500) {
        mensaje = "Ocurrió un error interno en el servidor.";
    } else {
        mensaje = "Ocurrió un error inesperado.";
    }

    modelo.addAttribute("codigo", codigoError);
    modelo.addAttribute("mensaje", mensaje);

    return "error"; // abre error.html
}
}