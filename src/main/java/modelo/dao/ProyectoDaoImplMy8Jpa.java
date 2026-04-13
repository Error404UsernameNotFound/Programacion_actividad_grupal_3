package modelo.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import jakarta.persistence.EntityManager;
import modelo.entities.Proyecto;

public class ProyectoDaoImplMy8Jpa implements ProyectoDao {

    private EntityManager em;

    public ProyectoDaoImplMy8Jpa(EntityManager em) {
        this.em = em;
    }

    // =====================
    // CRUD
    // =====================

    @Override
    public void create(Proyecto p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    @Override
    public Proyecto findById(String id) {
        return em.find(Proyecto.class, id);
    }

    @Override
    public List<Proyecto> findAll() {
        return em.createQuery("SELECT p FROM Proyecto p", Proyecto.class)
                .getResultList();
    }

    @Override
    public void update(Proyecto p) {
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Proyecto p) {
        em.getTransaction().begin();
        em.remove(em.merge(p));
        em.getTransaction().commit();
    }

    @Override
    public List<Proyecto> proyectosByEstado(String estado) {
        return em.createQuery("SELECT p FROM Proyecto p WHERE p.estado = :estado", Proyecto.class)
                .setParameter("estado", estado)
                .getResultList();
    }

    @Override
    public List<Proyecto> proyectosByCliente(String cif) {
        return em.createQuery("SELECT p FROM Proyecto p WHERE p.cif = :cif", Proyecto.class)
                .setParameter("cif", cif)
                .getResultList();
    }

    @Override
    public List<Proyecto> proyectosByJefeProyectoAndEstado(int jefeProyecto, String estado) {
        return em.createQuery("SELECT p FROM Proyecto p WHERE p.jefe_proyecto = :jefe AND p.estado = :estado", Proyecto.class)
                .setParameter("jefe", jefeProyecto)
                .setParameter("estado", estado)
                .getResultList();
    }

    @Override
    public double importesVentaProyectosTerminados() {
        Double resultado = em.createQuery(
                "SELECT SUM(p.venta_previsto) FROM Proyecto p WHERE p.estado = 'TERMINADO'",
                Double.class
        ).getSingleResult();

        return resultado != null ? resultado : 0;
    }

    @Override
    public double margenBrutoProyectosTerminados() {
        Double ventas = em.createQuery(
                "SELECT SUM(p.venta_previsto) FROM Proyecto p WHERE p.estado = 'TERMINADO'",
                Double.class
        ).getSingleResult();

        Double costes = em.createQuery(
                "SELECT SUM(p.coste_real) FROM Proyecto p WHERE p.estado = 'TERMINADO'",
                Double.class
        ).getSingleResult();

        double v = (ventas != null) ? ventas : 0;
        double c = (costes != null) ? costes : 0;

        return v - c;
    }

    @Override
    public double porcentajeMargenBrutoProyectosTerminados() {
        double ventas = importesVentaProyectosTerminados();
        if (ventas == 0) return 0;

        return (margenBrutoProyectosTerminados() / ventas) * 100;
    }

    @Override
    public int diasATerminoProyectoActivo(String codigoProyecto) {

        Proyecto p = findById(codigoProyecto);

        if (p == null || p.getFecha_fin_previsto() == null) return 0;

        return (int) ChronoUnit.DAYS.between(
                LocalDate.now(),
                p.getFecha_fin_previsto()
        );
    }
}