package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Entidades.Estadio;
import Excepciones.DatabaseException;
import Persistencia.ICrud;

public class EstadioServicio {

    private ICrud<Estadio> persistencia;
	
	public EstadioServicio(ICrud<Estadio> persistencia) {
		this.persistencia = persistencia;
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

    }

}
