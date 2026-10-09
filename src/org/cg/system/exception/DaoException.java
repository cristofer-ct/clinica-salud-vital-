package org.cg.system.exception;

/**
 * Excepción de la capa de acceso a datos.
 * Se lanza cuando una operación contra la base de datos falla.
 */
public class DaoException extends RuntimeException {

    public DaoException(String mensaje) {
        super(mensaje);
    }

    public DaoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
