package org.cg.system.dao;

import java.util.ArrayList;
import org.cg.system.model.Cita;

public interface CitaDAO {

    boolean insertar(Cita cita);

    boolean actualizar(Cita cita);

    boolean eliminar(int id);

    ArrayList<Cita> listarTodos();

    Cita buscarPorId(int id);
}
