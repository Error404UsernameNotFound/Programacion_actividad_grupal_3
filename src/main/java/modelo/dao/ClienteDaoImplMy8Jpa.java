package modelo.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modelo.entities.Cliente;

public class ClienteDaoImplMy8Jpa implements ClienteDao {

    private EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("proyectosFP");

    @Override
    public void alta(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente buscar(String cif) {
        EntityManager em = emf.createEntityManager();
        Cliente cliente = em.find(Cliente.class, cif);
        em.close();
        return cliente;
    }

    @Override
    public List<Cliente> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Cliente> lista = em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                                .getResultList();
        em.close();
        return lista;
    }

    @Override
    public void eliminar(String cif) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente cliente = em.find(Cliente.class, cif);
            if (cliente != null) {
                em.remove(cliente);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
