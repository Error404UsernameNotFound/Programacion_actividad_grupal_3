package modelo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "proyecto_con_productos")
public class ProyectoConProducto {

    @Id
    @Column(name = "num_orden")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numOrden;
    @Column(name = "precio_asignado")
    private int precioAsignado;
    @ManyToOne
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    public ProyectoConProducto() {
    }

    public ProyectoConProducto(int numOrden, int precioAsignado, 
                                Proyecto proyecto, Producto producto) {
        super();
        this.numOrden = numOrden;
        this.precioAsignado = precioAsignado;
        this.proyecto = proyecto;
        this.producto = producto;
    }

    public int getNumOrden() { return numOrden; }
    public void setNumOrden(int numOrden) { this.numOrden = numOrden; }

    public int getPrecioAsignado() { return precioAsignado; }
    public void setPrecioAsignado(int precioAsignado) { this.precioAsignado = precioAsignado; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    @Override
    public String toString() {
        return "ProyectoConProducto [numOrden=" + numOrden 
               + ", precioAsignado=" + precioAsignado + "]";
    }
}
