package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Entidades.Estadio;
import Entidades.Ubicacion;
import Excepciones.DatabaseException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.ICrud;

public class UbicacionServicio {

    private ICrud<Ubicacion> persistencia;

    public UbicacionServicio(ICrud<Ubicacion> pers) {
        this.persistencia = pers;
    }

    public List<Ubicacion> list() throws DatabaseException, RegistroNotFoundExeption {

        List<Ubicacion> ubicacions = null;

        try {
            ubicacions = persistencia.getList();
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return ubicacions;
    }

    public void insert(String nombre, Estadio estadio, Double precio, Integer capacidad) throws DatabaseException {
        try {
            Ubicacion ub = new Ubicacion(nombre, capacidad, precio, estadio);

            persistencia.save(ub);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void edit(Integer id, String name, Double precio, Integer capacidad, Estadio estadio) throws DatabaseException, RegistroNotFoundExeption {

        try {
            Ubicacion ub = persistencia.get(id);

            ub.setNombre(name);
            ub.setCapacidad(capacidad);
            ub.setPrecio(precio);
            ub.setEstadio(estadio);

            persistencia.update(ub);


        } catch (ClassNotFoundException | IOException | SQLException  e) {
            e.printStackTrace();
            throw new DatabaseException();
        }


    }

    public void delete(Integer id) throws DatabaseException, RegistroNotFoundExeption {
        try {
            Ubicacion ub = persistencia.get(id);

            if (ub != null) {
                persistencia.delete(ub);
            }

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

}
