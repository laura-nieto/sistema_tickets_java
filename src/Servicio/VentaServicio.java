package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import Entidades.Entrada;
import Entidades.Espectaculo;
import Entidades.Usuario;
import Excepciones.DatabaseException;
import Excepciones.EspacioRestanteException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.ICrud;

public class VentaServicio {

    private ICrud<Entrada> persEntrada;
    private ICrud<Espectaculo> persEspectaculo;

    public VentaServicio(ICrud<Entrada> persEntr , ICrud<Espectaculo> persEspe) {
        this.persEntrada = persEntr;
        this.persEspectaculo = persEspe;
    }


    public List<Espectaculo> listEspectaculos() throws DatabaseException, RegistroNotFoundExeption {

        List<Espectaculo> espectaculos = null;

        String sql = "SELECT id, name, estadio_id, timestamp, price FROM espectaculos WHERE timestamp >= ? ORDER BY name";

        Timestamp ahora = Timestamp.valueOf(LocalDateTime.now());

        try {
            espectaculos = persEspectaculo.getList(sql, ahora);

            for (Espectaculo espectaculo : espectaculos) {
                
                String sql2 = "SELECT id, document, buyer_name, espectaculo_id, vendedor, soldAt FROM entradas WHERE espectaculo_id = ?";

                List<Entrada> entradasEspectaculo = persEntrada.getList(sql2, espectaculo.getId());

                espectaculo.setEntradas(entradasEspectaculo);

            }

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return espectaculos;
    }

    public Entrada sell(Usuario user, String nombre, String documento, Integer idEspectaculo) throws RegistroNotFoundExeption, EspacioRestanteException {

        Entrada entrada = null;

        try {

            Timestamp hoy = Timestamp.valueOf(LocalDateTime.now());

            Espectaculo espectaculo = persEspectaculo.get(idEspectaculo);

            // Cargo las entradas para verificar que todavía haya espacio
            String sql = "SELECT id, document, buyer_name, espectaculo_id, vendedor, soldAt FROM entradas WHERE espectaculo_id = ?";

            List<Entrada> entradasEspectaculo = persEntrada.getList(sql, espectaculo.getId());

            espectaculo.setEntradas(entradasEspectaculo);

            if (espectaculo.espacioRestante() < 1) {
                throw new EspacioRestanteException();
            }

            entrada = new Entrada(documento, nombre, espectaculo, user, hoy);

            persEntrada.save(entrada);

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }

        return entrada;
    }
}
