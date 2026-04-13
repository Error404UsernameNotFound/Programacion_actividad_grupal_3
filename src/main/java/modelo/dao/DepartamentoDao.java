package modelo.dao;

import java.util.List;

import modelo.entities.Departamento;

public interface DepartamentoDao {

	void alta(Departamento departamento);

	Departamento editar(Departamento departamento);

	Departamento buscar(int idDepar);

	List<Departamento> findAll();

	void eliminar(int idDepar);
}