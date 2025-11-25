package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import Entidades.Espectaculo;
import Entidades.Estadio;
import Excepciones.DatabaseException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.ICrud;

public class EspectaculosServicio {

    private ICrud<Espectaculo> persistencia;

    public EspectaculosServicio(ICrud<Espectaculo> persistencia) {
        this.persistencia = persistencia;
    }

    public Espectaculo get(Integer id) {
        Espectaculo espec = null;

        try {
            espec = persistencia.get(id);
        } catch (ClassNotFoundException | IOException | SQLException | RegistroNotFoundExeption e) {
            e.printStackTrace();
        }

        return espec;
    }

    public List<Espectaculo> list() throws DatabaseException, RegistroNotFoundExeption {

        List<Espectaculo> espectaculos = null;

        try {
            espectaculos = persistencia.getList();
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return espectaculos;
    }

    public void insert(String nombre, Estadio estadio, Timestamp fecha) throws DatabaseException{

        try {
            Espectaculo espectaculo = new Espectaculo(nombre, estadio, fecha);

            persistencia.save(espectaculo);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void edit(Integer id, String nombre, Estadio estadio, Timestamp timestamp) throws DatabaseException, RegistroNotFoundExeption {
        try {
            Espectaculo espectaculo = persistencia.get(id);

            espectaculo.setName(nombre);
            espectaculo.setEstadio(estadio);
            espectaculo.setTimestamp(timestamp);

            persistencia.update(espectaculo);

        } catch (ClassNotFoundException | IOException | SQLException  e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void delete(Integer id) throws RegistroNotFoundExeption, DatabaseException {
        try {
            Espectaculo espectaculo = persistencia.get(id);
            persistencia.delete(espectaculo);

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }
}
