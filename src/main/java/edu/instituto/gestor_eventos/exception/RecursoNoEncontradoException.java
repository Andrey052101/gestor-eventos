package edu.instituto.gestor_eventos.exception;

/** Se lanza cuando el recurso pedido no existe (el handler la convierte en 404). */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}