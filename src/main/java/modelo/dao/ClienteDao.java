package modelo.dao;

import java.util.List;
import modelo.entities.Cliente;

public interface ClienteDao {
    void alta(Cliente cliente);
    Cliente buscar(String cif);
    List<Cliente> findAll();
    void eliminar(String cif);
}
