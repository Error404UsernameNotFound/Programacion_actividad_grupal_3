package modelo.daos;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.entities.Departamento;

public class DepartamentoDaoImplMy8Jpa implements DepartamentoDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectosFP");

	public DepartamentoDaoImplMy8Jpa() {
	}

	@Override
	public void alta(Departamento departamento) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(departamento);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public Departamento editar(Departamento departamento) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Departamento departamentoEditado = null;
		try {
			tx.begin();
			departamentoEditado = em.merge(departamento);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
		return departamentoEditado;
	}

	@Override
	public Departamento buscar(int idDepar) {
		EntityManager em = emf.createEntityManager();
		Departamento departamento = em.find(Departamento.class, idDepar);
		em.close();
		return departamento;
	}

	@Override
	public List<Departamento> findAll() {
		EntityManager em = emf.createEntityManager();
		List<Departamento> lista = em
				.createQuery("select d from Departamento d order by d.idDepar", Departamento.class)
				.getResultList();
		em.close();
		return lista;
	}

	@Override
	public void eliminar(int idDepar) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Departamento departamento = em.find(Departamento.class, idDepar);
			if (departamento != null) {
				em.remove(departamento);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
}
