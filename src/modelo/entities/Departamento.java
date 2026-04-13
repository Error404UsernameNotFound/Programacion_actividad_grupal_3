package modelo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "departamentos")
public class Departamento {

	@Id
	@Column(name = "id_depar")
	private int idDepar;

	@Column(name = "nombre", length = 45, nullable = false)
	private String nombre;

	@Column(name = "direccion", length = 100)
	private String direccion;

	public Departamento() {
	}

	public Departamento(int idDepar, String nombre, String direccion) {
		this.idDepar = idDepar;
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public int getIdDepar() {
		return idDepar;
	}

	public void setIdDepar(int idDepar) {
		this.idDepar = idDepar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Departamento [idDepar=" + idDepar + ", nombre=" + nombre + ", direccion=" + direccion + "]";
	}
}
