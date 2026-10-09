package org.cg.system.dao;

import java.util.ArrayList;
import org.cg.system.model.Especialidad;

public interface EspecialidadDAO {

    boolean insertar(Especialidad especialidad);

    boolean actualizar(Especialidad especialidad);

    boolean eliminar(int id);

    ArrayList<Especialidad> listarTodos();

    Especialidad buscarPorId(int id);
}
