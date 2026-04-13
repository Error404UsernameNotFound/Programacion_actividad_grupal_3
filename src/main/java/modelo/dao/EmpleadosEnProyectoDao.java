package modelo.dao;

import modelo.entities.EmpleadosEnProyecto;
import java.util.List;

public interface EmpleadosEnProyectoDao {

    // CRUD
    void create(EmpleadosEnProyecto e);
    EmpleadosEnProyecto findById(int numeroOrden);
    List<EmpleadosEnProyecto> findAll();
    void update(EmpleadosEnProyecto e);
    void delete(EmpleadosEnProyecto e);

    // =====================
    // MÉTODOS OBLIGATORIOS
    // =====================

    List<EmpleadosEnProyecto> empleadosByProyecto(String codigoProyecto);

    int asignarEmpleadosAProyecto(List<EmpleadosEnProyecto> empleados);

    int horasAsignadasAProyecto(String codigoProyecto);

    double costeActualDeEmpleadosEnProyecto(String codigoProyecto);
}