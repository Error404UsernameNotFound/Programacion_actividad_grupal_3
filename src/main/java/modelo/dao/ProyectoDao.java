package modelo.dao;

import modelo.entities.Proyecto;
import java.util.List;

public interface ProyectoDao {

    void create(Proyecto p);
    Proyecto findById(String id);
    List<Proyecto> findAll();
    void update(Proyecto p);
    void delete(Proyecto p);

    List<Proyecto> proyectosByEstado(String estado);

    List<Proyecto> proyectosByCliente(String cif);

    List<Proyecto> proyectosByJefeProyectoAndEstado(int jefeProyecto, String estado);

    double importesVentaProyectosTerminados();

    double margenBrutoProyectosTerminados();

    double porcentajeMargenBrutoProyectosTerminados();

    int diasATerminoProyectoActivo(String codigoProyecto);
}