# Proyecto para la actividad grupal del tercer trimestre de Programación

## Integrantes del equipo:
 
 - David Gutiérrez Jacob
 - Laia Martínez Motis
 - Lucía Armas Acosta
 - Raúl Mínguez Rodríguez

 ## Estructura del proyecto

- **Proyecto/**
  - **src/**
    - **principales/**  
      - `GestionClientes.java` → Clase principal con `main`  
      - `ImprimirGastos.java` → Utilidad para imprimir gastos
    - **modelo/**
      - **entities/**  
        - `Cliente.java` → Entidad JPA  
        - `Proyecto.java` → Entidad JPA  
        - `ProyectoConEmpleados.java` → Entidad JPA  
        - `Empleado.java` → Entidad JPA  
        - `ProyectoConProductos.java` → Entidad JPA  
        - `Departamento.java` → Entidad JPA  
        - `Producto.java` → Entidad JPA  
        - `Factura.java` → Entidad JPA  
        - `Familia.java` → Entidad JPA  
        - `Perfil.java` → Entidad JPA
      - **daos/**  
        - `ClienteDao<I>.java` → Interfaz DAO  
        - `ClienteDaoImplMy8Jpa.java` → Implementación DAO  
        - `ProyectoDao<I>.java`  
        - `ProyectoDaoImplMy8Jpa.java`  
        - `ProyectoConEmpleadosDao<I>.java`  
        - `ProyectoConEmpleadosDaoImplMy8Jpa.java`  
        - `EmpleadoDao<I>.java`  
        - `EmpleadoDaoImplMy8Jpa.java`  
        - `ProyectoConProductosDao<I>.java`  
        - `ProyectoConProductosDaoImplMy8Jpa.java`  
        - `DepartamentoDao<I>.java`  
        - `DepartamentoDaoImplMy8Jpa.java`  
        - `ProductoDao<I>.java`  
        - `ProductoDaoImplMy8Jpa.java`  
        - `FacturaDao<I>.java`  
        - `FacturaDaoImplMy8Jpa.java`  
        - `FamiliaDao<I>.java`  
        - `FamiliaDaoImplMy8Jpa.java`  
        - `PerfilDao<I>.java`  
        - `PerfilDaoImplMy8Jpa.java`
    - **test/**
      - **entities/**  
        - `TestCliente.java`  
        - `TestProyecto.java`  
        - `TestProyectoConEmpleados.java`  
        - `TestEmpleado.java`  
        - `TestProyectoConProductos.java`  
        - `TestDepartamento.java`  
        - `TestProducto.java`  
        - `TestFactura.java`  
        - `TestFamilia.java`  
        - `TestPerfil.java`
      - **daos/**  
        - `TestClienteDao.java`  
        - `TestProyectoDao.java`  
        - `TestProyectoConEmpleadosDao.java`  
        - `TestEmpleadoDao.java`  
        - `TestProyectoConProductosDao.java`  
        - `TestDepartamentoDao.java`  
        - `TestProductoDao.java`  
        - `TestFacturaDao.java`  
        - `TestFamiliaDao.java`  
        - `TestPerfilDao.java`
    - **resources/**  
      - `script.sql` → Script SQL de inicialización de la base de datos
  - `README.md` → Este archivo de documentación  
  - `.gitignore` → Configuración de archivos ignorados por Git

