package org.cg.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.cg.system.dao.UsuarioDAO;
import org.cg.system.exception.DaoException;
import org.cg.system.model.Usuario;
import org.cg.system.util.Conexion;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario iniciarSesion(String username, String passwordHash) {
        String sql = "{call sp_usuario_login(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, username);
            cs.setString(2, passwordHash);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al iniciar sesión: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean insertar(Usuario usuario) {
        String sql = "{call sp_usuario_insertar(?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, usuario.getUsername());
            cs.setString(2, usuario.getEmail());
            cs.setString(3, usuario.getFirstName());
            cs.setString(4, usuario.getLastName());
            cs.setString(5, usuario.getPasswordHash());
            cs.setString(6, usuario.getRol());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "{call sp_usuario_actualizar(?,?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, usuario.getId());
            cs.setString(2, usuario.getUsername());
            cs.setString(3, usuario.getEmail());
            cs.setString(4, usuario.getFirstName());
            cs.setString(5, usuario.getLastName());
            cs.setString(6, usuario.getRol());
            cs.setBoolean(7, usuario.isActivo());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{call sp_usuario_eliminar(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean cambiarPassword(int idUsuario, String passwordHash) {
        String sql = "{call sp_usuario_cambiar_password(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idUsuario);
            cs.setString(2, passwordHash);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al cambiar password: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Usuario> listarTodos() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "{call sp_usuario_listar()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar usuarios: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "{call sp_usuario_buscar_por_id(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar usuario: " + e.getMessage(), e);
        }
        return null;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setFirstName(rs.getString("first_name"));
        u.setLastName(rs.getString("last_name"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        u.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        return u;
    }
}
