package org.cg.system.dao;

import java.util.ArrayList;
import org.cg.system.model.Usuario;

public interface UsuarioDAO {

    Usuario iniciarSesion(String username, String passwordHash);

    boolean insertar(Usuario usuario);

    boolean actualizar(Usuario usuario);

    boolean eliminar(int id);

    boolean cambiarPassword(int idUsuario, String passwordHash);

    ArrayList<Usuario> listarTodos();

    Usuario buscarPorId(int id);
}
