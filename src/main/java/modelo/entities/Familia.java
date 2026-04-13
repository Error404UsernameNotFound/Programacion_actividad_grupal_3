package modelo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "familias")
public class Familia {

    @Id
    @Column(name = "id_familia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFamilia;
    @Column(name = "descripcion")
    private String descripcion;

    public Familia() {
    }

    public Familia(int idFamilia, String descripcion) {
        super();
        this.idFamilia = idFamilia;
        this.descripcion = descripcion;
    }

    public int getIdFamilia() { return idFamilia; }
    public void setIdFamilia(int idFamilia) { this.idFamilia = idFamilia; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Familia [idFamilia=" + idFamilia + ", descripcion=" + descripcion + "]";
    }
}