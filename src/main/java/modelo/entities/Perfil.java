package modelo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @Column(name = "id_perfil")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPerfil;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tasa_standard")
    private double tasaStandard;

    public Perfil() {
    }

    public Perfil(int idPerfil, String nombre, double tasaStandard) {
        super();
        this.idPerfil = idPerfil;
        this.nombre = nombre;
        this.tasaStandard = tasaStandard;
    }

    public int getIdPerfil() { return idPerfil; }
    public void setIdPerfil(int idPerfil) { this.idPerfil = idPerfil; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getTasaStandard() { return tasaStandard; }
    public void setTasaStandard(double tasaStandard) { this.tasaStandard = tasaStandard; }

    @Override
    public String toString() {
        return "Perfil [idPerfil=" + idPerfil + ", nombre=" + nombre 
               + ", tasaStandard=" + tasaStandard + "]";
    }
}
