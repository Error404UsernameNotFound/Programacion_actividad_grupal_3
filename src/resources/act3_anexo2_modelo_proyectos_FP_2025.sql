create database proyectos_FP_2025;
use proyectos_FP_2025;

create table clientes
( cif varchar(10) not null primary key,
nombre varchar(20) not null,
apellidos varchar(45) not null,
domicilio varchar(100),
facturacion_anual dec (15,2),
numero_empleados int
);
INSERT INTO `clientes` (`cif`, `nombre`, `apellidos`, `domicilio`, `facturacion_anual`, `numero_empleados`) 
	VALUES ('A11111111', 'Eva', 'González Pérez', 'Madrid', '850000', '20');
INSERT INTO `clientes` (`cif`, `nombre`, `apellidos`, `domicilio`, `facturacion_anual`, `numero_empleados`) 
	VALUES ('A11111112', 'Ramón', 'Benito Díaz', 'Sevilla', '15000000', '123');
INSERT INTO `clientes` (`cif`, `nombre`, `apellidos`, `domicilio`, `facturacion_anual`, `numero_empleados`) 
	VALUES ('A22222222', 'Carlos', 'March', 'Madrid', '12000000', '1500');
INSERT INTO `clientes` (`cif`, `nombre`, `apellidos`, `domicilio`, `facturacion_anual`, `numero_empleados`) 
	VALUES ('B33333333', 'Sara', 'Varas', 'Sevilla', '1500000', '345');


create table departamentos
(id_depar int primary key,
nombre varchar(45) not null,
direccion varchar(100)
);
insert into departamentos 
values(10,'Gestion Personas','Madrid'),
(20,'Software','Madrid'),
(30,'Hardware','Madrid'),
(40,'Financiero','Sevilla');
create table perfiles
( id_perfil int not null auto_increment primary key,
nombre varchar(20),
tasa_standard dec(7,2)
);
insert into perfiles
(nombre, tasa_standard)
	values('Control de Gestion', 120),('Jefe de Proyecto',220), ('Operativo',100),('Recusos Humanos',250);
    
create table empleados 
( id_empl int not null auto_increment primary key,
nombre varchar(20) not null,
apellidos varchar(45) not null,
genero char(1) not null,
email varchar(100) not null unique,
password varchar(45) not null,
salario dec (9,2),
fecha_ingreso date,
fecha_nacimiento date,
id_perfil int not null,
id_depar int not null,
check(genero in('H','h','M','m')),
foreign key(id_depar) references departamentos(id_depar),
foreign key(id_perfil) references perfiles (id_perfil)
);
INSERT INTO `empleados`VALUES ('100', 'esteban', 'Diaz', 'H', 'ediaz@tt.com', 'esteban',90000, '1990-01-12', '1977-02-12', 4, 10);
INSERT INTO `empleados` VALUES ('101', 'Sara', 'Hernandez', 'M', 'shernandez@tt.com','sara', '45000', '2005-07-07', '1987-05-15', '4', '10');
INSERT INTO`empleados` VALUES ('114', 'Rafael', 'Raphaelly', 'H',  'rraphaelly@tt.com','rafael', '78000', '2005-09-07', '1977-02-18', '2', '20');
INSERT INTO `empleados`  VALUES ('115', 'Carlos', 'Koo', 'H', 'ckoo@tt.com','carlos', '33000', '2015-09-07', '1983-02-04', '3', '20');
INSERT INTO `empleados`  VALUES ('116', 'Carmen', 'Baida', 'M',  'cbaida@tt.com','carmen', '32000', '2015-09-08', '1983-12-04', '3', '20');
INSERT INTO `empleados`  VALUES ('117', 'Alejandro', 'Himuro', 'H', 'ahimuro@tt.com','alejandro', '25000', '2015-09-09', '1984-12-04', '3', '20');
INSERT INTO `empleados` VALUES ('118', 'Eva', 'Colmenares', 'M', 'ecolmenares@tt.com','eva', '25000', '2015-09-09', '1984-12-04', '3', '20');
INSERT INTO `empleados` VALUES ('119', 'Eva', 'Tobias','M',  'etobias@tt.com','eva', '25000', '2015-09-09', '1984-12-04', '3', '20');
INSERT INTO `empleados`  VALUES ('120', 'Raquel', 'Oliva', 'M', 'roliva@tt.com','raquel', '38000', '2015-09-09', '1982-04-21', '1', '40');


-- drop table proyectos;
create table proyectos
( id_proyecto varchar(10) not null primary key,
descripcion varchar(45) not null,
fecha_inicio date,
fecha_fin_previsto date,
fecha_fin_real date,
venta_previsto dec(11,2),
costes_previsto dec(11,2),
coste_real dec (11,2),
estado varchar(15),
jefe_proyecto int,
cif varchar(10) not null,
check(estado in('ACTIVO','TERMINADO')),
foreign key(cif) references clientes(cif),
foreign key(jefe_proyecto) references empleados(id_empl)

);

INSERT INTO `proyectos` (`id_proyecto`, `descripcion`, `fecha_inicio`, `fecha_fin_previsto`, `fecha_fin_real`, `venta_previsto`, `costes_previsto`, `coste_real`, `estado`, `jefe_proyecto`, `cif`) VALUES ('FOR2020001', 'Formacion de habilidades directivas', '2020-01-15', '2020-07-31', '2020-07-31', '50000', '30000', '315000', 'TERMINADO', '114', 'A22222222');
INSERT INTO `proyectos` (`id_proyecto`, `descripcion`, `fecha_inicio`, `fecha_fin_previsto`, `fecha_fin_real`, `venta_previsto`, `costes_previsto`, `coste_real`, `estado`, `jefe_proyecto`, `cif`) VALUES ('FOR2021001', 'Formacion de jefes de proyecto', '2021-09-15', '2021-12-31', null, '50000', '30000', null, 'ACTIVO', '114', 'A22222222');
INSERT INTO `proyectos` (`id_proyecto`, `descripcion`, `fecha_inicio`, `fecha_fin_previsto`, `fecha_fin_real`, `venta_previsto`, `costes_previsto`, `coste_real`, `estado`, `jefe_proyecto`, `cif`) VALUES ('FOR2021002', 'Formacion de jefes de proyecto', '2021-09-15', '2021-12-31', null, '50000', '30000', null, 'ACTIVO', '114', 'A22222222');

create table proyecto_con_empleados
( numero_orden int auto_increment primary key,
id_proyecto varchar(10) not null,
id_empl int not null,
horas_asignadas int not null,
fecha_incorporacion date,
foreign key(id_proyecto) references proyectos(id_proyecto),
foreign key(id_empl) references empleados(id_empl)
);
INSERT INTO `proyecto_con_empleados` (`numero_orden`, `id_proyecto`, `id_empl`, `horas_asignadas`, `fecha_incorporacion`) VALUES ('1', 'FOR2020001', '115', '45', '2020-01-16');
INSERT INTO `proyecto_con_empleados` (`numero_orden`, `id_proyecto`, `id_empl`, `horas_asignadas`, `fecha_incorporacion`) VALUES ('2', 'FOR2020001', '116', '30', '2020-01-17');
INSERT INTO `proyecto_con_empleados` (`numero_orden`, `id_proyecto`, `id_empl`, `horas_asignadas`, `fecha_incorporacion`) VALUES ('3', 'FOR2021001', '117', '54', '2021-10-11');
INSERT INTO `proyecto_con_empleados` (`numero_orden`, `id_proyecto`, `id_empl`, `horas_asignadas`, `fecha_incorporacion`) VALUES ('4', 'FOR2021001', '118', '100', '2021-10-14');

create table familias
(id_familia int not null primary key auto_increment,
descripcion varchar(45) not NULL
);
insert into familias (descripcion) values('Licencias');
insert into familias (descripcion) values('Hardware');
insert into familias (descripcion) values('Salas');
insert into familias (descripcion) values('Software');

create table productos
(id_producto int not null primary key auto_increment,
descripcion varchar(45) not null,
precio dec(7,2),
fecha_creacion date,
id_familia int not null,
foreign key(id_familia) references familias(id_familia)
);

insert into productos (descripcion, precio, fecha_creacion, id_familia)
values('Impresora laser brother 550-DS', 125,'2018-12-05',2);

create table proyecto_con_productos
( num_orden int not null primary key auto_increment,
id_proyecto varchar(10) not null,
id_producto int not null,
precio_asignado int not null,
foreign key(id_proyecto) references proyectos(id_proyecto),
foreign key(id_producto) references productos(id_producto)
);


create table facturas
( id_factura varchar(15) not null primary key,
descripcion varchar(45) not null,
fecha_factura date,
id_proyecto varchar(10) not null,
foreign key(id_proyecto) references proyectos(id_proyecto)
);

INSERT INTO `facturas` (`id_factura`, `descripcion`, fecha_factura,`id_proyecto`) VALUES ('F2020001', 'Formacion a cliente 1','2023-01-12', 'FOR2020001');

 
-- create user 'uproyectos_IFP_2023' identified by 'uproyectos';
-- grant all privileges on clientes_proyectos_empleados_IFP_2023.* to  'uproyectos_IFP_2023';
 