package org.cg.system.exception;

/**
 * Excepción de negocio / aplicación.
 */
public class AppException extends RuntimeException {

    public AppException(String mensaje) {
        super(mensaje);
    }

    public AppException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
