package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Entidades.Espectaculo;
import Excepciones.DatabaseException;
import Persistencia.ICrud;

public class EspectaculosServicio {

    ICrud<Espectaculo> persistencia;

    public EspectaculosServicio(ICrud<Espectaculo> persistencia) {
        this.persistencia = persistencia;
    }

    public List<Espectaculo> list() throws DatabaseException {

        List<Espectaculo> espectaculos = null;

        try {
            espectaculos = persistencia.get();
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return espectaculos;
    }
}
