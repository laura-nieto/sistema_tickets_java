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

}
