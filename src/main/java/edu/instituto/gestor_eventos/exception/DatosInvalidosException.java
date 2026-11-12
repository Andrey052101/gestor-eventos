package edu.instituto.gestor_eventos.exception;

/** Se lanza cuando los datos no cumplen una regla del negocio (el handler la convierte en 400). */
public class DatosInvalidosException extends RuntimeException {

    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}