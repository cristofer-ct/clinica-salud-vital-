package org.cg.system.main;

import java.util.ArrayList;
import org.cg.system.dao.impl.CitaDAOImpl;
import org.cg.system.dao.impl.EspecialidadDAOImpl;
import org.cg.system.dao.impl.MedicoDAOImpl;
import org.cg.system.dao.impl.PacienteDAOImpl;
import org.cg.system.dao.impl.UsuarioDAOImpl;
import org.cg.system.model.Cita;
import org.cg.system.model.Especialidad;
import org.cg.system.model.Medico;
import org.cg.system.model.Paciente;
import org.cg.system.model.Usuario;
import org.cg.system.util.Conexion;

/**
 * Punto de entrada temporal (Fases 1-4).
 * Verifica conexion y lista datos de prueba.
 * En Fase 5 se reemplazara por Application JavaFX.
 */
public class Principal {

    public static void main(String[] args) {
        System.out.println("=== Clinica Medica SaludVital ===");
        System.out.println("Verificando conexion a MySQL...");

        try {
            Conexion.getInstancia().conectar().close();
            System.out.println("[OK] Conexion establecida correctamente.\n");
        } catch (Exception e) {
            System.err.println("[ERROR] No se pudo conectar: " + e.getMessage());
            System.err.println("Revisa src/db.properties y que MySQL este corriendo.");
            System.err.println("Luego: Clean and Build en NetBeans e intenta de nuevo.");
            return;
        }

        try {
            UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
            ArrayList<Usuario> usuarios = usuarioDAO.listarTodos();
            System.out.println("--- Usuarios (" + usuarios.size() + ") ---");
            for (Usuario u : usuarios) {
                System.out.println("  " + u.getId() + " | " + u.getUsername() + " | " + u.getRol());
            }

            EspecialidadDAOImpl espDAO = new EspecialidadDAOImpl();
            ArrayList<Especialidad> especialidades = espDAO.listarTodos();
            System.out.println("\n--- Especialidades (" + especialidades.size() + ") ---");
            for (Especialidad e : especialidades) {
                System.out.println("  " + e.getId() + " | " + e.getNombre());
            }

            MedicoDAOImpl medicoDAO = new MedicoDAOImpl();
            ArrayList<Medico> medicos = medicoDAO.listarTodos();
            System.out.println("\n--- Medicos (" + medicos.size() + ") ---");
            for (Medico m : medicos) {
                System.out.println("  " + m.getId() + " | " + m.getNombreCompleto()
                        + " | " + m.getEspecialidadNombre());
            }

            PacienteDAOImpl pacienteDAO = new PacienteDAOImpl();
            ArrayList<Paciente> pacientes = pacienteDAO.listarTodos();
            System.out.println("\n--- Pacientes (" + pacientes.size() + ") ---");
            for (Paciente p : pacientes) {
                System.out.println("  " + p.getId() + " | " + p.getNombreCompleto()
                        + " | DPI: " + p.getDpi());
            }

            CitaDAOImpl citaDAO = new CitaDAOImpl();
            ArrayList<Cita> citas = citaDAO.listarTodos();
            System.out.println("\n--- Citas (" + citas.size() + ") ---");
            for (Cita c : citas) {
                System.out.println("  " + c.getId() + " | " + c.getPacienteNombre()
                        + " con " + c.getMedicoNombre() + " | " + c.getEstado()
                        + " | " + c.getFechaHora());
            }

            System.out.println("\n--- Login de prueba (admin / admin123) ---");
            Usuario sesion = usuarioDAO.iniciarSesion("admin", "admin123");
            if (sesion != null) {
                System.out.println("[OK] Login exitoso: " + sesion.getNombreCompleto()
                        + " (" + sesion.getRol() + ")");
            } else {
                System.out.println("[FAIL] Credenciales incorrectas o usuario inactivo.");
            }

            System.out.println("\n=== Fase 1-4 completada correctamente ===");
        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
}
