package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import Entidades.Entrada;
import Entidades.Espectaculo;
import Entidades.Ubicacion;
import Entidades.Usuario;
import Excepciones.DatabaseException;
import Excepciones.EspacioRestanteException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.ICrud;

public class VentaServicio {

    private ICrud<Entrada> persEntrada;
    private ICrud<Espectaculo> persEspectaculo;
    private ICrud<Ubicacion> persUbicacion;

    public VentaServicio(ICrud<Entrada> persEntr , ICrud<Espectaculo> persEspe, ICrud<Ubicacion> persUb) {
        this.persEntrada = persEntr;
        this.persEspectaculo = persEspe;
        this.persUbicacion = persUb;
    }

    public List<Espectaculo> listEspectaculos() throws DatabaseException, RegistroNotFoundExeption {

        List<Espectaculo> espectaculos = null;

        String sql = "SELECT id, name, estadio_id, timestamp FROM espectaculos WHERE timestamp >= ? ORDER BY name";

        Timestamp ahora = Timestamp.valueOf(LocalDateTime.now());

        try {
            espectaculos = persEspectaculo.getList(sql, ahora);

            for (Espectaculo espectaculo : espectaculos) {
                
                String sql2 = "SELECT id, buyer_document, buyer_name, espectaculo_id, ubicacion_id, vendedor_id, soldAt FROM entradas WHERE espectaculo_id = ?";

                List<Entrada> entradasEspectaculo = persEntrada.getList(sql2, espectaculo.getId());

                espectaculo.setEntradas(entradasEspectaculo);
            }

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return espectaculos;
    }

    public Espectaculo getEspectaculo(Integer id) throws DatabaseException, RegistroNotFoundExeption {
        
        Espectaculo espec = null;

        try {
            espec = persEspectaculo.get(id);
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return espec;
    }

    public List<Ubicacion> listUbicaciones(Integer idEstadio) throws DatabaseException, RegistroNotFoundExeption {

        String sql = "SELECT id, estadio_id, nombre, capacidad, precio FROM estadio_ubicaciones WHERE estadio_id = ? ORDER BY nombre";

        List<Ubicacion> ubicacions = null;

        try {
            ubicacions = persUbicacion.getList(sql, idEstadio);
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        } catch (RegistroNotFoundExeption e) {
            e.printStackTrace();
            throw e;
        }

        return ubicacions;
    }

    public Entrada sell(Usuario user, String nombre, String documento, Integer idEspectaculo, Integer idUbicacion) throws RegistroNotFoundExeption, EspacioRestanteException {

        Entrada entrada = null;

        try {

            Timestamp hoy = Timestamp.valueOf(LocalDateTime.now());

            Espectaculo espectaculo = persEspectaculo.get(idEspectaculo);

            // Cargo las entradas para verificar que todavía haya espacio
            String sql = "SELECT id, buyer_document, buyer_name, espectaculo_id, ubicacion_id, vendedor_id, soldAt FROM entradas WHERE espectaculo_id = ?";

            List<Entrada> entradasEspectaculo = persEntrada.getList(sql, espectaculo.getId());

            espectaculo.setEntradas(entradasEspectaculo);

            if (espectaculo.espacioRestante() < 1) {
                throw new EspacioRestanteException();
            }

            Ubicacion ubicacion = persUbicacion.get(idUbicacion);

            entrada = new Entrada(documento, nombre, espectaculo, user, hoy, ubicacion);

            persEntrada.save(entrada);

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }

        return entrada;
    }
}
