package test.daos;

import java.util.List;

import modelo.daos.DepartamentoDao;
import modelo.daos.DepartamentoDaoImplMy8Jpa;
import modelo.entities.Departamento;

public class TestDepartamentoDao {

	private static final String SEPARADOR = "============================================================";

	public static void main(String[] args) {
		DepartamentoDao dao = new DepartamentoDaoImplMy8Jpa();
		Departamento departamentoPrueba = new Departamento(99, "Departamento Prueba", "Valencia");

		try {
			bloque("1. ALTA");
			System.out.println("Insertando departamento de prueba...");
			dao.alta(departamentoPrueba);
			System.out.println("OK -> Insertado con id " + departamentoPrueba.getIdDepar());

			
			bloque("2. BUSQUEDA POR ID");
			Departamento encontrado = dao.buscar(10);
			System.out.println("Resultado: " + resumenDepartamento(encontrado));
			
			encontrado = dao.buscar(99);
			System.out.println("Resultado: " + resumenDepartamento(encontrado));

			
			bloque("3. EDICION");
			System.out.println("Actualizando direccion a Barcelona...");
			encontrado.setDireccion("Barcelona");
			dao.editar(encontrado);
			System.out.println("Resultado tras editar: " + resumenDepartamento(dao.buscar(99)));

			
			bloque("4. FIND ALL");
			List<Departamento> todos = dao.findAll();
			System.out.println("Total departamentos en BD: " + todos.size());
			mostrarPrimerResultado(todos);

			
			bloque("5. BAJA");
			System.out.println("Eliminando departamento de prueba con id 99...");
			dao.eliminar(99);
			System.out.println("Busqueda tras borrar: " + resumenDepartamento(dao.buscar(99)));

			
			bloque("FIN TEST DEPARTAMENTO DAO");
			System.out.println("Prueba finalizada sin errores.");
		} catch (Exception e) {
			bloque("ERROR EN TEST");
			System.err.println("Error durante la prueba de integracion de DepartamentoDao:");
			e.printStackTrace();
		}
	}

	private static void bloque(String titulo) {
		System.out.println();
		System.out.println(SEPARADOR);
		System.out.println(titulo);
		System.out.println(SEPARADOR);
	}

	private static void mostrarPrimerResultado(List<Departamento> departamentos) {
		if (departamentos == null || departamentos.isEmpty()) {
			System.out.println("Sin resultados.");
			return;
		}
		System.out.println("Primer resultado: " + resumenDepartamento(departamentos.get(0)));
	}

	private static String resumenDepartamento(Departamento departamento) {
		if (departamento == null) {
			return "null";
		}
		return "id=" + departamento.getIdDepar() + ", nombre=" + departamento.getNombre() + ", direccion="
				+ departamento.getDireccion();
	}
}
