package org.cg.system.dao;

import java.util.ArrayList;
import org.cg.system.model.Paciente;

public interface PacienteDAO {

    boolean insertar(Paciente paciente);

    boolean actualizar(Paciente paciente);

    boolean eliminar(int id);

    ArrayList<Paciente> listarTodos();

    Paciente buscarPorId(int id);
}
