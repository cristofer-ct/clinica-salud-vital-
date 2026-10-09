package org.cg.system.manager;

import org.cg.system.model.Usuario;
import java.sql.Timestamp;

/**
 * Singleton en memoria que guarda el usuario autenticado,
 * su rol y la hora de inicio de sesión.
 */
public class SessionContext {

    private static SessionContext instancia;
    private Usuario usuarioActual;
    private Timestamp horaInicioSesion;

    private SessionContext() {
    }

    public static synchronized SessionContext getInstancia() {
        if (instancia == null) {
            instancia = new SessionContext();
        }
        return instancia;
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
        this.horaInicioSesion = new Timestamp(System.currentTimeMillis());
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
        this.horaInicioSesion = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    public String getRolActual() {
        return usuarioActual != null ? usuarioActual.getRol() : null;
    }

    public Timestamp getHoraInicioSesion() {
        return horaInicioSesion;
    }
}
