package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Entidades.Estadio;
import Excepciones.DatabaseException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.ICrud;

public class EstadioServicio {

    private ICrud<Estadio> persistencia;
	
	public EstadioServicio(ICrud<Estadio> persistencia) {
		this.persistencia = persistencia;
	}

    public Estadio get(Integer id) {
        Estadio estadio = null;

        try {
            estadio = persistencia.get(id);
        } catch (ClassNotFoundException | IOException | SQLException | RegistroNotFoundExeption e) {
            e.printStackTrace();
        }

        return estadio;
    }

    public List<Estadio> list() throws DatabaseException {

        List<Estadio> estadios = null;

        try {
            estadios = persistencia.get();
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return estadios;
    }

    public void insert(String nombre, Integer capacidad, String direccion) throws DatabaseException {

        try {
            Estadio est = new Estadio(nombre, direccion, capacidad);

            persistencia.save(est);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void edit(Integer id, String nombre, Integer capacidad, String direccion) throws DatabaseException, RegistroNotFoundExeption {
        try {
            Estadio estadio = persistencia.get(id);

            estadio.setName(nombre);
            estadio.setCapacity(capacidad);
            estadio.setAddress(direccion);
            
            persistencia.update(estadio);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void delete(Integer id) throws RegistroNotFoundExeption, DatabaseException {
        try {
            Estadio estadio = persistencia.get(id);
            persistencia.delete(estadio);

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

}
