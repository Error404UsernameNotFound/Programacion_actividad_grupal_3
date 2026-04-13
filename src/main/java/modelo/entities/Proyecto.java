package modelo.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    private String id;

    private String descripcion;

    private LocalDate fecha_inicio;
    private LocalDate fecha_fin_previsto;
    private LocalDate fecha_fin_real;

    private double venta_previsto;
    private double costes_previsto;
    private double coste_real;

    private String estado;

    private int jefe_proyecto;

    private String cif;

    public Proyecto() {}
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(LocalDate fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public LocalDate getFecha_fin_previsto() { return fecha_fin_previsto; }
    public void setFecha_fin_previsto(LocalDate fecha_fin_previsto) { this.fecha_fin_previsto = fecha_fin_previsto; }

    public LocalDate getFecha_fin_real() { return fecha_fin_real; }
    public void setFecha_fin_real(LocalDate fecha_fin_real) { this.fecha_fin_real = fecha_fin_real; }

    public double getVenta_previsto() { return venta_previsto; }
    public void setVenta_previsto(double venta_previsto) { this.venta_previsto = venta_previsto; }

    public double getCostes_previsto() { return costes_previsto; }
    public void setCostes_previsto(double costes_previsto) { this.costes_previsto = costes_previsto; }

    public double getCoste_real() { return coste_real; }
    public void setCoste_real(double coste_real) { this.coste_real = coste_real; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getJefe_proyecto() { return jefe_proyecto; }
    public void setJefe_proyecto(int jefe_proyecto) { this.jefe_proyecto = jefe_proyecto; }

    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }

    public double margenPrevisto() {
        return venta_previsto - costes_previsto;
    }

    public double porcentajeMargenPrevisto() {
        if (venta_previsto == 0) return 0;
        return (margenPrevisto() / venta_previsto) * 100;
    }

    public double margenReal() {
        return venta_previsto - coste_real;
    }

    public double porcentajeMargenReal() {
        if (venta_previsto == 0) return 0;
        return (margenReal() / venta_previsto) * 100;
    }

    public double diferenciaGastos() {
        return coste_real - costes_previsto;
    }

    public int diferenciaDiasFinPrevistoReal() {
        if (fecha_fin_real == null || fecha_fin_previsto == null) return 0;
        return (int) ChronoUnit.DAYS.between(fecha_fin_previsto, fecha_fin_real);
    }
}