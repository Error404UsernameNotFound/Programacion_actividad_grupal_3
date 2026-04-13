package modelo.dao;

import java.util.List;

import modelo.entities.Empleado;

public interface EmpleadoDao {

	void alta(Empleado empleado);

	Empleado editar(Empleado empleado);

	Empleado buscar(int idEmpl);

	List<Empleado> findAll();

	void eliminar(int idEmpl);

	List<Empleado> empleadosByDepartamento(int idDepar);

	List<Empleado> empleadosByGenero(char sexo);

	List<Empleado> empleadosByApellido(String subcadena);

	double salarioTotal();

	double salarioTotal(int idDepar);
}