package org.cg.system.dao;

import java.util.ArrayList;
import org.cg.system.model.Medico;

public interface MedicoDAO {

    boolean insertar(Medico medico);

    boolean actualizar(Medico medico);

    boolean eliminar(int id);

    ArrayList<Medico> listarTodos();

    Medico buscarPorId(int id);
}
