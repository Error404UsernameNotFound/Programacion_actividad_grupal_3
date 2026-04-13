package principales;

import java.util.List;
import java.util.Scanner;
import modelo.dao.ClienteDao;
import modelo.dao.ClienteDaoImplMy8Jpa;
import modelo.entities.Cliente;

public class GestionClientes {

    public static void main(String[] args) {
        ClienteDao dao = new ClienteDaoImplMy8Jpa();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("GESTIÓN DE CLIENTES");
            System.out.println("1. Alta del Cliente");
            System.out.println("2. Buscar un Cliente");
            System.out.println("3. Mostrar Todos");
            System.out.println("4. Eliminar un Cliente");
            System.out.println("5. Salir");
            System.out.print("Elige opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("CIF: ");
                    String cif = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Apellidos: ");
                    String apellidos = sc.nextLine();
                    System.out.print("Domicilio: ");
                    String domicilio = sc.nextLine();
                    System.out.print("Facturación anual: ");
                    double facturacion = sc.nextDouble();
                    System.out.print("Número de empleados: ");
                    int numEmp = sc.nextInt();
                    sc.nextLine();
                    Cliente nuevo = new Cliente(cif, nombre, apellidos, 
                                               domicilio, facturacion, numEmp);
                    dao.alta(nuevo);
                    System.out.println("Cliente dado de alta correctamente.");
                    break;
                case 2:
                    System.out.print("Introduce el CIF: ");
                    String cifBuscar = sc.nextLine();
                    Cliente encontrado = dao.buscar(cifBuscar);
                    if (encontrado != null) {
                        System.out.println(encontrado);
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;
                case 3:
                    List<Cliente> todos = dao.findAll();
                    if (todos.isEmpty()) {
                        System.out.println("No hay clientes.");
                    } else {
                        for (Cliente c : todos) {
                            System.out.println(c);
                        }
                    }
                    break;
                case 4:
                    System.out.print("Introduce el CIF a eliminar: ");
                    String cifEliminar = sc.nextLine();
                    dao.eliminar(cifEliminar);
                    System.out.println("Cliente eliminado (si existía).");
                    break;
                case 5:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        sc.close();
    }
}