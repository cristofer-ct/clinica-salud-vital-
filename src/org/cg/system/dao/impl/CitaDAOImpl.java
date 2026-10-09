package org.cg.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.cg.system.dao.CitaDAO;
import org.cg.system.exception.DaoException;
import org.cg.system.model.Cita;
import org.cg.system.util.Conexion;

public class CitaDAOImpl implements CitaDAO {

    @Override
    public boolean insertar(Cita c) {
        String sql = "{call sp_cita_insertar(?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, c.getPacienteId());
            cs.setInt(2, c.getMedicoId());
            cs.setTimestamp(3, c.getFechaHora());
            cs.setString(4, c.getEstado());
            cs.setString(5, c.getMotivo());
            cs.setString(6, c.getNotas());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar cita: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizar(Cita c) {
        String sql = "{call sp_cita_actualizar(?,?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, c.getId());
            cs.setInt(2, c.getPacienteId());
            cs.setInt(3, c.getMedicoId());
            cs.setTimestamp(4, c.getFechaHora());
            cs.setString(5, c.getEstado());
            cs.setString(6, c.getMotivo());
            cs.setString(7, c.getNotas());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar cita: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{call sp_cita_eliminar(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar cita: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Cita> listarTodos() {
        ArrayList<Cita> lista = new ArrayList<>();
        String sql = "{call sp_cita_listar()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar citas: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Cita buscarPorId(int id) {
        String sql = "{call sp_cita_buscar_por_id(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar cita: " + e.getMessage(), e);
        }
        return null;
    }

    private Cita mapear(ResultSet rs) throws SQLException {
        Cita c = new Cita();
        c.setId(rs.getInt("id"));
        c.setPacienteId(rs.getInt("paciente_id"));
        c.setMedicoId(rs.getInt("medico_id"));
        c.setFechaHora(rs.getTimestamp("fecha_hora"));
        c.setEstado(rs.getString("estado"));
        c.setMotivo(rs.getString("motivo"));
        c.setNotas(rs.getString("notas"));
        c.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        c.setPacienteNombre(rs.getString("paciente_nombre"));
        c.setMedicoNombre(rs.getString("medico_nombre"));
        return c;
    }
}
