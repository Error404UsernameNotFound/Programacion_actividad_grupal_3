package modelo.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empl")
	private int idEmpl;

	@Column(name = "nombre", length = 20, nullable = false)
	private String nombre;

	@Column(name = "apellidos", length = 45, nullable = false)
	private String apellidos;

	@Column(name = "genero", length = 1, nullable = false)
	private char genero;

	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;

	@Column(name = "password", length = 45, nullable = false)
	private String password;

	@Column(name = "salario")
	private double salario;

	@Column(name = "fecha_ingreso")
	private LocalDate fechaIngreso;

	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;

	@Column(name = "id_perfil", nullable = false)
	private int idPerfil;

	@Column(name = "id_depar", nullable = false)
	private int idDepar;

	public Empleado() {
	}

	public Empleado(int idEmpl, String nombre, String apellidos, char genero, String email, String password,
			double salario, LocalDate fechaIngreso, LocalDate fechaNacimiento, int idPerfil, int idDepar) {
		this.idEmpl = idEmpl;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.genero = genero;
		this.email = email;
		this.password = password;
		this.salario = salario;
		this.fechaIngreso = fechaIngreso;
		this.fechaNacimiento = fechaNacimiento;
		this.idPerfil = idPerfil;
		this.idDepar = idDepar;
	}

	public int getIdEmpl() {
		return idEmpl;
	}

	public void setIdEmpl(int idEmpl) {
		this.idEmpl = idEmpl;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	public int getIdDepar() {
		return idDepar;
	}

	public void setIdDepar(int idDepar) {
		this.idDepar = idDepar;
	}

	public double salarioMensual(int meses) {
		if (meses <= 0) {
			return 0;
		}
		return salario / meses;
	}

	public String literalGenero() {
		return Character.toUpperCase(genero) == 'H' ? "Hombre" : "Mujer";
	}

	public String nombreCompleto() {
		return nombre + " " + apellidos;
	}

	@Override
	public String toString() {
		return "Empleado [idEmpl=" + idEmpl + ", nombre=" + nombre + ", apellidos=" + apellidos + ", genero=" + genero
				+ ", email=" + email + ", salario=" + salario + ", fechaIngreso=" + fechaIngreso + ", fechaNacimiento="
				+ fechaNacimiento + ", idPerfil=" + idPerfil + ", idDepar=" + idDepar + "]";
	}
}
