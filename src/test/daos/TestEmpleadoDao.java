package test.daos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modelo.daos.EmpleadoDao;
import modelo.daos.EmpleadoDaoImplMy8Jpa;
import modelo.entities.Empleado;

public class TestEmpleadoDao {

	public static void main(String[] args) {
		EmpleadoDao dao = new EmpleadoDaoImplMy8Jpa();
		String sufijo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		Empleado empleadoPrueba = new Empleado(0, "Sara", "HernandezPrueba" + sufijo, 'M',
				"sara." + sufijo + "@tt.com", "1234", 45000, LocalDate.of(2020, 1, 10), LocalDate.of(1990, 5, 20), 4,
				10);

		try {
			System.out.println("1. Insertando empleado de prueba...");
			dao.alta(empleadoPrueba);
			int idGenerado = empleadoPrueba.getIdEmpl();
			System.out.println("ID generado: " + idGenerado);

			System.out.println("\n2. Buscando por ID...");
			Empleado encontrado = dao.buscar(idGenerado);
			System.out.println(encontrado);

			System.out.println("\n3. Probando metodos propios de la entidad...");
			System.out.println("Nombre completo: " + encontrado.nombreCompleto());
			System.out.println("Genero literal: " + encontrado.literalGenero());
			System.out.println("Salario mensual en 12 meses: " + encontrado.salarioMensual(12));

			System.out.println("\n4. Editando salario...");
			encontrado.setSalario(48000);
			dao.editar(encontrado);
			System.out.println("Empleado editado: " + dao.buscar(idGenerado));

			System.out.println("\n5. Consultando findAll...");
			List<Empleado> todos = dao.findAll();
			System.out.println("Total empleados: " + todos.size());

			System.out.println("\n6. Filtrando por departamento 10...");
			System.out.println(dao.empleadosByDepartamento(10));

			System.out.println("\n7. Filtrando por genero M...");
			System.out.println(dao.empleadosByGenero('M'));

			System.out.println("\n8. Filtrando por apellido...");
			System.out.println(dao.empleadosByApellido("Prueba" + sufijo));

			System.out.println("\n9. Sumando salarios...");
			System.out.println("Salario total empresa: " + dao.salarioTotal());
			System.out.println("Salario total departamento 10: " + dao.salarioTotal(10));

			System.out.println("\n10. Eliminando empleado de prueba...");
			dao.eliminar(idGenerado);
			System.out.println("Busqueda tras borrar: " + dao.buscar(idGenerado));
		} catch (Exception e) {
			System.err.println("Error durante la prueba de integracion de EmpleadoDao:");
			e.printStackTrace();
		}
	}
}
