package modelo.dao;

import jakarta.persistence.EntityManager;
import modelo.entities.EmpleadosEnProyecto;

import java.util.List;

public class EmpleadosEnProyectoDaoImplMy8Jpa implements EmpleadosEnProyectoDao {

    private EntityManager em;

    public EmpleadosEnProyectoDaoImplMy8Jpa(EntityManager em) {
        this.em = em;
    }

    // =====================
    // CRUD
    // =====================

    @Override
    public void create(EmpleadosEnProyecto e) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    @Override
    public EmpleadosEnProyecto findById(int numeroOrden) {
        return em.find(EmpleadosEnProyecto.class, numeroOrden);
    }

    @Override
    public List<EmpleadosEnProyecto> findAll() {
        return em.createQuery("SELECT e FROM EmpleadosEnProyecto e", EmpleadosEnProyecto.class)
                .getResultList();
    }

    @Override
    public void update(EmpleadosEnProyecto e) {
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }

    @Override
    public void delete(EmpleadosEnProyecto e) {
        em.getTransaction().begin();
        em.remove(em.merge(e));
        em.getTransaction().commit();
    }

    // =====================
    // CONSULTAS
    // =====================

    @Override
    public List<EmpleadosEnProyecto> empleadosByProyecto(String codigoProyecto) {
        return em.createQuery(
                "SELECT e FROM EmpleadosEnProyecto e WHERE e.id_proyecto = :codigo",
                EmpleadosEnProyecto.class
        ).setParameter("codigo", codigoProyecto)
         .getResultList();
    }

    // =====================
    // OPERACIONES
    // =====================

    @Override
    public int asignarEmpleadosAProyecto(List<EmpleadosEnProyecto> empleados) {

        em.getTransaction().begin();

        int count = 0;

        for (EmpleadosEnProyecto e : empleados) {
            em.persist(e);
            count++;
        }

        em.getTransaction().commit();

        return count;
    }

    @Override
    public int horasAsignadasAProyecto(String codigoProyecto) {

        Integer resultado = em.createQuery(
                "SELECT SUM(e.horas_asignadas) FROM EmpleadosEnProyecto e WHERE e.id_proyecto = :codigo",
                Integer.class
        ).setParameter("codigo", codigoProyecto)
         .getSingleResult();

        return resultado != null ? resultado : 0;
    }

    @Override
    public double costeActualDeEmpleadosEnProyecto(String codigoProyecto) {

        // ⚠️ Sin relación con Empleado, no podemos hacer JOIN real
        // Solución: simplificada o asumida

        List<EmpleadosEnProyecto> lista = empleadosByProyecto(codigoProyecto);

        double total = 0;

        for (EmpleadosEnProyecto e : lista) {
            // ⚠️ AQUÍ NECESITARÍAS EL PRECIO/HORA REAL
            double precioHora = 20; // placeholder
            total += e.getHoras_asignadas() * precioHora;
        }

        return total;
    }
}