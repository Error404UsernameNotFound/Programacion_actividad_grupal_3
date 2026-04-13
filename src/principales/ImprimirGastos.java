package principales;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ImprimirGastos {

	private static final String PERSISTENCE_UNIT = "proyectosFP";
	private static final String PROYECTO_DEMO = "GASTOS0001";
	private static final DecimalFormat FORMATO = new DecimalFormat("0.00");

	private final EntityManagerFactory emf;

	public ImprimirGastos() {
		this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	public static void main(String[] args) {
		new ImprimirGastos().ejecutar();
	}

	public void ejecutar() {
		try {
			String codigoProyecto = asegurarProyectoTerminadoConDatos();
			InformeGastos informe = cargarInforme(codigoProyecto);
			actualizarCosteReal(informe.codigoProyecto(), informe.totalGastado());
			imprimirInforme(informe);
		} finally {
			if (emf.isOpen()) {
				emf.close();
			}
		}
	}

	private String asegurarProyectoTerminadoConDatos() {
		EntityManager em = emf.createEntityManager();
		try {
			@SuppressWarnings("unchecked")
			List<String> proyectos = em.createNativeQuery(
					"select p.id_proyecto from proyectos p "
							+ "where upper(p.estado) = 'TERMINADO' "
							+ "and exists (select 1 from proyecto_con_empleados pe where pe.id_proyecto = p.id_proyecto) "
							+ "and exists (select 1 from proyecto_con_productos pp where pp.id_proyecto = p.id_proyecto) "
							+ "order by p.id_proyecto")
					.getResultList();

			if (!proyectos.isEmpty()) {
				return proyectos.get(0);
			}
		} finally {
			em.close();
		}

		crearProyectoDemoSiNoExiste();
		return PROYECTO_DEMO;
	}

	private void crearProyectoDemoSiNoExiste() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Object existente = em.createNativeQuery("select count(*) from proyectos where id_proyecto = ?")
					.setParameter(1, PROYECTO_DEMO)
					.getSingleResult();

			if (((Number) existente).intValue() == 0) {
				em.createNativeQuery(
						"insert into proyectos "
								+ "(id_proyecto, descripcion, fecha_inicio, fecha_fin_previsto, fecha_fin_real, venta_previsto, costes_previsto, coste_real, estado, jefe_proyecto, cif) "
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
						.setParameter(1, PROYECTO_DEMO)
						.setParameter(2, "Proyecto demo informe")
						.setParameter(3, LocalDate.of(2025, 1, 10))
						.setParameter(4, LocalDate.of(2025, 3, 15))
						.setParameter(5, LocalDate.of(2025, 3, 20))
						.setParameter(6, 80000)
						.setParameter(7, 50000)
						.setParameter(8, 0)
						.setParameter(9, "TERMINADO")
						.setParameter(10, 114)
						.setParameter(11, "A22222222")
						.executeUpdate();
			}

			insertarRelacionEmpleadoSiNoExiste(em, PROYECTO_DEMO, 115, 80, LocalDate.of(2025, 1, 12));
			insertarRelacionEmpleadoSiNoExiste(em, PROYECTO_DEMO, 116, 65, LocalDate.of(2025, 1, 15));
			insertarRelacionProductoSiNoExiste(em, PROYECTO_DEMO, 1, 125);
			insertarRelacionProductoSiNoExiste(em, PROYECTO_DEMO, 1, 125);

			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	private void insertarRelacionEmpleadoSiNoExiste(EntityManager em, String proyecto, int empleado, int horas,
			LocalDate fecha) {
		Object existente = em.createNativeQuery(
				"select count(*) from proyecto_con_empleados where id_proyecto = ? and id_empl = ?")
				.setParameter(1, proyecto)
				.setParameter(2, empleado)
				.getSingleResult();

		if (((Number) existente).intValue() == 0) {
			em.createNativeQuery(
					"insert into proyecto_con_empleados (id_proyecto, id_empl, horas_asignadas, fecha_incorporacion) "
							+ "values (?, ?, ?, ?)")
					.setParameter(1, proyecto)
					.setParameter(2, empleado)
					.setParameter(3, horas)
					.setParameter(4, fecha)
					.executeUpdate();
		}
	}

	private void insertarRelacionProductoSiNoExiste(EntityManager em, String proyecto, int producto, int precio) {
		Object existente = em.createNativeQuery(
				"select count(*) from proyecto_con_productos where id_proyecto = ? and id_producto = ? and precio_asignado = ?")
				.setParameter(1, proyecto)
				.setParameter(2, producto)
				.setParameter(3, precio)
				.getSingleResult();

		if (((Number) existente).intValue() == 0) {
			em.createNativeQuery(
					"insert into proyecto_con_productos (id_proyecto, id_producto, precio_asignado) values (?, ?, ?)")
					.setParameter(1, proyecto)
					.setParameter(2, producto)
					.setParameter(3, precio)
					.executeUpdate();
		}
	}

	private InformeGastos cargarInforme(String codigoProyecto) {
		EntityManager em = emf.createEntityManager();
		try {
			Object[] proyecto = (Object[]) em.createNativeQuery(
					"select c.nombre, c.domicilio, p.id_proyecto, p.descripcion, p.fecha_inicio, p.fecha_fin_real, p.venta_previsto "
							+ "from proyectos p join clientes c on p.cif = c.cif where p.id_proyecto = ?")
					.setParameter(1, codigoProyecto)
					.getSingleResult();

			List<GastoEmpleado> gastosEmpleados = cargarGastosEmpleados(em, codigoProyecto);
			List<GastoProducto> gastosProductos = cargarGastosProductos(em, codigoProyecto);

			double totalHoras = 0;
			double totalEmpleados = 0;
			for (GastoEmpleado gastoEmpleado : gastosEmpleados) {
				totalHoras += gastoEmpleado.horas();
				totalEmpleados += gastoEmpleado.importeRepercutido();
			}

			double totalProductos = 0;
			for (GastoProducto gastoProducto : gastosProductos) {
				totalProductos += gastoProducto.total();
			}

			double totalGastado = totalEmpleados + totalProductos;

			return new InformeGastos(
					String.valueOf(proyecto[2]),
					String.valueOf(proyecto[0]),
					String.valueOf(proyecto[1]),
					String.valueOf(proyecto[3]),
					aLocalDate(proyecto[4]),
					aLocalDate(proyecto[5]),
					((Number) proyecto[6]).doubleValue(),
					gastosEmpleados,
					gastosProductos,
					totalHoras,
					totalEmpleados,
					totalProductos,
					totalGastado);
		} finally {
			em.close();
		}
	}

	private List<GastoEmpleado> cargarGastosEmpleados(EntityManager em, String codigoProyecto) {
		@SuppressWarnings("unchecked")
		List<Object[]> filas = em.createNativeQuery(
				"select e.apellidos, e.nombre, pe.horas_asignadas, pf.tasa_standard, (pe.horas_asignadas * pf.tasa_standard) "
						+ "from proyecto_con_empleados pe "
						+ "join empleados e on pe.id_empl = e.id_empl "
						+ "join perfiles pf on e.id_perfil = pf.id_perfil "
						+ "where pe.id_proyecto = ? order by e.apellidos, e.nombre")
				.setParameter(1, codigoProyecto)
				.getResultList();

		List<GastoEmpleado> gastos = new ArrayList<>();
		for (Object[] fila : filas) {
			gastos.add(new GastoEmpleado(
					String.valueOf(fila[0]) + ", " + String.valueOf(fila[1]),
					((Number) fila[2]).doubleValue(),
					((Number) fila[3]).doubleValue(),
					((Number) fila[4]).doubleValue()));
		}
		return gastos;
	}

	private List<GastoProducto> cargarGastosProductos(EntityManager em, String codigoProyecto) {
		@SuppressWarnings("unchecked")
		List<Object[]> filas = em.createNativeQuery(
				"select pr.descripcion, count(*), avg(pp.precio_asignado), sum(pp.precio_asignado) "
						+ "from proyecto_con_productos pp "
						+ "join productos pr on pp.id_producto = pr.id_producto "
						+ "where pp.id_proyecto = ? "
						+ "group by pr.descripcion order by pr.descripcion")
				.setParameter(1, codigoProyecto)
				.getResultList();

		List<GastoProducto> gastos = new ArrayList<>();
		for (Object[] fila : filas) {
			gastos.add(new GastoProducto(
					String.valueOf(fila[0]),
					((Number) fila[1]).intValue(),
					((Number) fila[2]).doubleValue(),
					((Number) fila[3]).doubleValue()));
		}
		return gastos;
	}

	private void actualizarCosteReal(String codigoProyecto, double totalGastado) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.createNativeQuery("update proyectos set coste_real = ? where id_proyecto = ?")
					.setParameter(1, totalGastado)
					.setParameter(2, codigoProyecto)
					.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	private void imprimirInforme(InformeGastos informe) {
		System.out.println("============================================================");
		System.out.println("INFORME DE GASTOS REALES");
		System.out.println("============================================================");
		System.out.println();
		System.out.println("DATOS DEL CLIENTE:");
		System.out.println("Nombre: " + informe.nombreCliente() + "  Direccion: " + informe.direccionCliente());
		System.out.println();
		System.out.println("DATOS DEL PROYECTO:");
		System.out.println("Codigo Proyecto: " + informe.codigoProyecto());
		System.out.println("Descripcion Proyecto: " + informe.descripcionProyecto());
		System.out.println("Fecha Inicio: " + informe.fechaInicio() + "  Fecha Fin real: " + informe.fechaFinReal());
		System.out.println();
		System.out.println("DETALLE DE RECURSOS EMPLEADOS:");
		System.out.println("LISTA EMPLEADOS");
		for (GastoEmpleado gastoEmpleado : informe.gastosEmpleados()) {
			System.out.println("- Apellidos, nombre: " + gastoEmpleado.nombreCompleto()
					+ "  Horas(total): " + sinDecimales(gastoEmpleado.horas())
					+ "  Precio/hora: " + importe(gastoEmpleado.precioHora())
					+ "  Importe repercutido: " + importe(gastoEmpleado.importeRepercutido()));
		}
		System.out.println("Total Horas: " + sinDecimales(informe.totalHoras())
				+ "  Total Precio: " + importe(informe.totalEmpleados()));
		System.out.println();
		System.out.println("LISTA PRODUCTOS");
		for (GastoProducto gastoProducto : informe.gastosProductos()) {
			System.out.println("- Descripcion: " + gastoProducto.descripcion()
					+ "  Cantidad: " + gastoProducto.cantidad()
					+ "  Precio por uno: " + importe(gastoProducto.precioUnitario())
					+ "  Total: " + importe(gastoProducto.total()));
		}
		System.out.println("Total Importes: " + importe(informe.totalProductos()));
		System.out.println();
		System.out.println("DETALLE DEL IMPORTE:");
		System.out.println("Importe Venta: " + importe(informe.importeVenta()));
		System.out.println("Total Gastado: " + importe(informe.totalGastado()));
		System.out.println();
		System.out.println("Coste real actualizado en BD para el proyecto " + informe.codigoProyecto() + ".");
	}

	private String importe(double valor) {
		return FORMATO.format(valor);
	}

	private LocalDate aLocalDate(Object valor) {
		if (valor == null) {
			return null;
		}
		if (valor instanceof LocalDate fechaLocal) {
			return fechaLocal;
		}
		if (valor instanceof Date fechaSql) {
			return fechaSql.toLocalDate();
		}
		return LocalDate.parse(String.valueOf(valor));
	}

	private String sinDecimales(double valor) {
		return String.valueOf((int) valor);
	}

	private record GastoEmpleado(String nombreCompleto, double horas, double precioHora, double importeRepercutido) {
	}

	private record GastoProducto(String descripcion, int cantidad, double precioUnitario, double total) {
	}

	private record InformeGastos(
			String codigoProyecto,
			String nombreCliente,
			String direccionCliente,
			String descripcionProyecto,
			LocalDate fechaInicio,
			LocalDate fechaFinReal,
			double importeVenta,
			List<GastoEmpleado> gastosEmpleados,
			List<GastoProducto> gastosProductos,
			double totalHoras,
			double totalEmpleados,
			double totalProductos,
			double totalGastado) {
	}
}
