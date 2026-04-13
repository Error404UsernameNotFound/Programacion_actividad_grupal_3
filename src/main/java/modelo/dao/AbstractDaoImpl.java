package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public abstract class AbstractDaoImpl {

    protected EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction tx;
    protected Query query;
    protected String jpql;
    protected int filas;

    public AbstractDaoImpl() {
        emf = Persistence.createEntityManagerFactory("proyectosFP");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }
}
