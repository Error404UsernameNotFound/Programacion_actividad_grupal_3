package modelo.daos;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.entities.Empleado;

public class EmpleadoDaoImplMy8Jpa implements EmpleadoDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectosFP");

	public EmpleadoDaoImplMy8Jpa() {
	}

	@Override
	public void alta(Empleado empleado) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(empleado);
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
	public Empleado editar(Empleado empleado) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Empleado empleadoEditado = null;
		try {
			tx.begin();
			empleadoEditado = em.merge(empleado);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
		return empleadoEditado;
	}

	@Override
	public Empleado buscar(int idEmpl) {
		EntityManager em = emf.createEntityManager();
		Empleado empleado = em.find(Empleado.class, idEmpl);
		em.close();
		return empleado;
	}

	@Override
	public List<Empleado> findAll() {
		EntityManager em = emf.createEntityManager();
		List<Empleado> lista = em.createQuery("select e from Empleado e order by e.idEmpl", Empleado.class)
				.getResultList();
		em.close();
		return lista;
	}

	@Override
	public void eliminar(int idEmpl) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Empleado empleado = em.find(Empleado.class, idEmpl);
			if (empleado != null) {
				em.remove(empleado);
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

	@Override
	public List<Empleado> empleadosByDepartamento(int idDepar) {
		EntityManager em = emf.createEntityManager();
		List<Empleado> lista = em.createQuery(
				"select e from Empleado e where e.idDepar = :idDepar order by e.apellidos, e.nombre", Empleado.class)
				.setParameter("idDepar", idDepar)
				.getResultList();
		em.close();
		return lista;
	}

	@Override
	public List<Empleado> empleadosByGenero(char sexo) {
		EntityManager em = emf.createEntityManager();
		List<Empleado> lista = em.createQuery(
				"select e from Empleado e where upper(e.genero) = :sexo order by e.apellidos, e.nombre", Empleado.class)
				.setParameter("sexo", String.valueOf(Character.toUpperCase(sexo)))
				.getResultList();
		em.close();
		return lista;
	}

	@Override
	public List<Empleado> empleadosByApellido(String subcadena) {
		EntityManager em = emf.createEntityManager();
		String textoBusqueda = subcadena == null ? "" : subcadena.toLowerCase();
		List<Empleado> lista = em.createQuery(
				"select e from Empleado e where lower(e.apellidos) like :subcadena order by e.apellidos, e.nombre",
				Empleado.class)
				.setParameter("subcadena", "%" + textoBusqueda + "%")
				.getResultList();
		em.close();
		return lista;
	}

	@Override
	public double salarioTotal() {
		EntityManager em = emf.createEntityManager();
		Double total = em.createQuery("select sum(e.salario) from Empleado e", Double.class).getSingleResult();
		em.close();
		return total != null ? total : 0;
	}

	@Override
	public double salarioTotal(int idDepar) {
		EntityManager em = emf.createEntityManager();
		Double total = em.createQuery("select sum(e.salario) from Empleado e where e.idDepar = :idDepar", Double.class)
				.setParameter("idDepar", idDepar)
				.getSingleResult();
		em.close();
		return total != null ? total : 0;
	}
}
