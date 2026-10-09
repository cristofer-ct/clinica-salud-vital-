package org.cg.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.cg.system.dao.EspecialidadDAO;
import org.cg.system.exception.DaoException;
import org.cg.system.model.Especialidad;
import org.cg.system.util.Conexion;

public class EspecialidadDAOImpl implements EspecialidadDAO {

    @Override
    public boolean insertar(Especialidad e) {
        String sql = "{call sp_especialidad_insertar(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, e.getNombre());
            cs.setString(2, e.getDescripcion());
            return cs.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException("Error al insertar especialidad: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean actualizar(Especialidad e) {
        String sql = "{call sp_especialidad_actualizar(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, e.getId());
            cs.setString(2, e.getNombre());
            cs.setString(3, e.getDescripcion());
            cs.setBoolean(4, e.isActivo());
            return cs.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException("Error al actualizar especialidad: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{call sp_especialidad_eliminar(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException("Error al eliminar especialidad: " + ex.getMessage(), ex);
        }
    }

    @Override
    public ArrayList<Especialidad> listarTodos() {
        ArrayList<Especialidad> lista = new ArrayList<>();
        String sql = "{call sp_especialidad_listar()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al listar especialidades: " + ex.getMessage(), ex);
        }
        return lista;
    }

    @Override
    public Especialidad buscarPorId(int id) {
        String sql = "{call sp_especialidad_buscar_por_id(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al buscar especialidad: " + ex.getMessage(), ex);
        }
        return null;
    }

    private Especialidad mapear(ResultSet rs) throws SQLException {
        Especialidad e = new Especialidad();
        e.setId(rs.getInt("id"));
        e.setNombre(rs.getString("nombre"));
        e.setDescripcion(rs.getString("descripcion"));
        e.setActivo(rs.getBoolean("activo"));
        return e;
    }
}
