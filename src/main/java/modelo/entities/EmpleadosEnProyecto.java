package modelo.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "empleados_en_proyecto")
public class EmpleadosEnProyecto {

    @Id
    private int numero_orden;

    private String id_proyecto;
    private int id_empl;

    private int horas_asignadas;
    private LocalDate fecha_incorporacion;

    public EmpleadosEnProyecto() {}

    public int getNumero_orden() { return numero_orden; }
    public void setNumero_orden(int numero_orden) { this.numero_orden = numero_orden; }

    public String getId_proyecto() { return id_proyecto; }
    public void setId_proyecto(String id_proyecto) { this.id_proyecto = id_proyecto; }

    public int getId_empl() { return id_empl; }
    public void setId_empl(int id_empl) { this.id_empl = id_empl; }

    public int getHoras_asignadas() { return horas_asignadas; }
    public void setHoras_asignadas(int horas_asignadas) { this.horas_asignadas = horas_asignadas; }

    public LocalDate getFecha_incorporacion() { return fecha_incorporacion; }
    public void setFecha_incorporacion(LocalDate fecha_incorporacion) { this.fecha_incorporacion = fecha_incorporacion; }


    public double costeHorasAsignadas(double precioHora) {
        return horas_asignadas * precioHora;
    }
}