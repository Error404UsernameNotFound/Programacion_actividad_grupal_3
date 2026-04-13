package test.daos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modelo.daos.EmpleadoDao;
import modelo.daos.EmpleadoDaoImplMy8Jpa;
import modelo.entities.Empleado;

public class TestEmpleadoDao {

	private static final String SEPARADOR = "============================================================";

	public static void main(String[] args) {
		EmpleadoDao dao = new EmpleadoDaoImplMy8Jpa();

		Empleado empleadoPrueba = new Empleado(0, "katia", "Hernandez", 'M',
				"katia." + "@tt.com", "1234", 45000, LocalDate.of(2025, 10, 10), LocalDate.of(1990, 5, 20), 4,
				10);

		try {
			bloque("1. ALTA");
			System.out.println("Insertando empleado de prueba...");
			dao.alta(empleadoPrueba);
			int idGenerado = empleadoPrueba.getIdEmpl();
			System.out.println("OK -> ID generado: " + idGenerado);

			
			bloque("2. BUSQUEDA POR ID");
			Empleado encontrado = dao.buscar(100);
			System.out.println("Resultado: " + resumenEmpleado(encontrado));
			
			encontrado = dao.buscar(idGenerado);
			System.out.println("Resultado: " + resumenEmpleado(encontrado));
			
			
			bloque("3. METODOS DE LA ENTIDAD");
			System.out.println("Nombre completo: " + encontrado.nombreCompleto());
			System.out.println("Genero literal: " + encontrado.literalGenero());
			System.out.println("Salario mensual en 12 meses: " + encontrado.salarioMensual(12));

			
			bloque("4. EDICION");
			System.out.println("Actualizando salario a 48000...");
			encontrado.setSalario(48000);
			dao.editar(encontrado);
			System.out.println("Resultado tras editar: " + resumenEmpleado(dao.buscar(idGenerado)));

			
			bloque("5. FIND ALL");
			List<Empleado> todos = dao.findAll();
			System.out.println("Total empleados en BD: " + todos.size());

			
			bloque("6. FILTRO POR DEPARTAMENTO");
			List<Empleado> empleadosDepartamento = dao.empleadosByDepartamento(10);
			System.out.println("Departamento 10 -> " + empleadosDepartamento.size() + " empleado(s)");
			mostrarPrimerResultado(empleadosDepartamento);

			
			bloque("7. FILTRO POR GENERO");
			List<Empleado> empleadosGenero = dao.empleadosByGenero('M');
			System.out.println("Genero M -> " + empleadosGenero.size() + " empleado(s)");
			mostrarPrimerResultado(empleadosGenero);

			
			bloque("8. FILTRO POR APELLIDO");
			List<Empleado> empleadosApellido = dao.empleadosByApellido("Hernandez");
			System.out.println("Coincidencias por apellido -> " + empleadosApellido.size() + " empleado(s)");
			mostrarPrimerResultado(empleadosApellido);

			
			bloque("9. SUMA DE SALARIOS");
			System.out.println("Salario total empresa: " + dao.salarioTotal());
			System.out.println("Salario total departamento 10: " + dao.salarioTotal(10));

			
			bloque("10. BAJA");
			System.out.println("Eliminando empleado de prueba con id " + idGenerado + "...");
			dao.eliminar(idGenerado);
			System.out.println("Busqueda tras borrar: " + dao.buscar(idGenerado));
			
			bloque("FIN TEST EMPLEADO DAO");
			System.out.println("Prueba finalizada sin errores.");
		} catch (Exception e) {
			bloque("ERROR EN TEST");
			System.err.println("Error durante la prueba de integracion de EmpleadoDao:");
			e.printStackTrace();
		}
	}

	private static void bloque(String titulo) {
		System.out.println();
		System.out.println(SEPARADOR);
		System.out.println(titulo);
		System.out.println(SEPARADOR);
	}

	private static void mostrarPrimerResultado(List<Empleado> empleados) {
		if (empleados == null || empleados.isEmpty()) {
			System.out.println("Sin resultados.");
			return;
		}
		System.out.println("Primer resultado: " + resumenEmpleado(empleados.get(0)));
	}

	private static String resumenEmpleado(Empleado empleado) {
		if (empleado == null) {
			return "null";
		}
		return "id=" + empleado.getIdEmpl() + ", nombre=" + empleado.nombreCompleto() + ", genero="
				+ empleado.literalGenero() + ", salario=" + empleado.getSalario() + ", departamento="
				+ empleado.getIdDepar();
	}
}
