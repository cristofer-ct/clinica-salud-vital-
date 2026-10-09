package org.cg.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.cg.system.dao.MedicoDAO;
import org.cg.system.exception.DaoException;
import org.cg.system.model.Medico;
import org.cg.system.util.Conexion;

public class MedicoDAOImpl implements MedicoDAO {

    @Override
    public boolean insertar(Medico m) {
        String sql = "{call sp_medico_insertar(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, m.getUsuarioId());
            cs.setString(2, m.getColegiado());
            cs.setInt(3, m.getEspecialidadId());
            cs.setString(4, m.getTelefono());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar médico: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizar(Medico m) {
        String sql = "{call sp_medico_actualizar(?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, m.getId());
            cs.setString(2, m.getColegiado());
            cs.setInt(3, m.getEspecialidadId());
            cs.setString(4, m.getTelefono());
            cs.setBoolean(5, m.isActivo());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar médico: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{call sp_medico_eliminar(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar médico: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Medico> listarTodos() {
        ArrayList<Medico> lista = new ArrayList<>();
        String sql = "{call sp_medico_listar()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar médicos: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Medico buscarPorId(int id) {
        String sql = "{call sp_medico_buscar_por_id(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar médico: " + e.getMessage(), e);
        }
        return null;
    }

    private Medico mapear(ResultSet rs) throws SQLException {
        Medico m = new Medico();
        m.setId(rs.getInt("id"));
        m.setUsuarioId(rs.getInt("usuario_id"));
        m.setColegiado(rs.getString("colegiado"));
        m.setEspecialidadId(rs.getInt("especialidad_id"));
        m.setTelefono(rs.getString("telefono"));
        m.setActivo(rs.getBoolean("activo"));
        m.setFirstName(rs.getString("first_name"));
        m.setLastName(rs.getString("last_name"));
        m.setEmail(rs.getString("email"));
        m.setEspecialidadNombre(rs.getString("especialidad_nombre"));
        return m;
    }
}
