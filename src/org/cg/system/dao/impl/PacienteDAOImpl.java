package org.cg.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.cg.system.dao.PacienteDAO;
import org.cg.system.exception.DaoException;
import org.cg.system.model.Paciente;
import org.cg.system.util.Conexion;

public class PacienteDAOImpl implements PacienteDAO {

    @Override
    public boolean insertar(Paciente p) {
        String sql = "{call sp_paciente_insertar(?,?,?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, p.getDpi());
            cs.setString(2, p.getNombre());
            cs.setString(3, p.getApellido());
            cs.setDate(4, p.getFechaNacimiento());
            cs.setString(5, p.getSexo());
            cs.setString(6, p.getTelefono());
            cs.setString(7, p.getEmail());
            cs.setString(8, p.getDireccion());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizar(Paciente p) {
        String sql = "{call sp_paciente_actualizar(?,?,?,?,?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, p.getId());
            cs.setString(2, p.getDpi());
            cs.setString(3, p.getNombre());
            cs.setString(4, p.getApellido());
            cs.setDate(5, p.getFechaNacimiento());
            cs.setString(6, p.getSexo());
            cs.setString(7, p.getTelefono());
            cs.setString(8, p.getEmail());
            cs.setString(9, p.getDireccion());
            cs.setBoolean(10, p.isActivo());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{call sp_paciente_eliminar(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Paciente> listarTodos() {
        ArrayList<Paciente> lista = new ArrayList<>();
        String sql = "{call sp_paciente_listar()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar pacientes: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Paciente buscarPorId(int id) {
        String sql = "{call sp_paciente_buscar_por_id(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar paciente: " + e.getMessage(), e);
        }
        return null;
    }

    private Paciente mapear(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getInt("id"));
        p.setDpi(rs.getString("dpi"));
        p.setNombre(rs.getString("nombre"));
        p.setApellido(rs.getString("apellido"));
        p.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        p.setSexo(rs.getString("sexo"));
        p.setTelefono(rs.getString("telefono"));
        p.setEmail(rs.getString("email"));
        p.setDireccion(rs.getString("direccion"));
        p.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }
}
