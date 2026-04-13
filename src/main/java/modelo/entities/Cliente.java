package modelo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @Column(name = "cif")
    private String cif;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "facturacion_anual")
    private double facturacionAnual;
    @Column(name = "numero_empleados")
    private int numeroEmpleados;

    public Cliente() {
    }

    public Cliente(String cif, String nombre, String apellidos, 
                   String domicilio, double facturacionAnual, int numeroEmpleados) {
        super();
        this.cif = cif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.facturacionAnual = facturacionAnual;
        this.numeroEmpleados = numeroEmpleados;
    }

    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public double getFacturacionAnual() { return facturacionAnual; }
    public void setFacturacionAnual(double facturacionAnual) { 
        this.facturacionAnual = facturacionAnual; 
    }

    public int getNumeroEmpleados() { return numeroEmpleados; }
    public void setNumeroEmpleados(int numeroEmpleados) { 
        this.numeroEmpleados = numeroEmpleados; 
    }

    @Override
    public int hashCode() {
        return cif != null ? cif.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente other = (Cliente) obj;
        return cif != null ? cif.equals(other.cif) : other.cif == null;
    }

    @Override
    public String toString() {
        return "Cliente [cif=" + cif + ", nombre=" + nombre + ", apellidos=" + apellidos 
               + ", domicilio=" + domicilio + ", facturacionAnual=" + facturacionAnual 
               + ", numeroEmpleados=" + numeroEmpleados + "]";
    }
}