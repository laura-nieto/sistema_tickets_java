package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entidades.Entrada;
import Entidades.Espectaculo;
import Entidades.Ubicacion;
import Excepciones.DatabaseException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.ICrud;

public class ReportesServicios {

    private ICrud<Entrada> persEntrada;
    private ICrud<Espectaculo> persEspectaculo;
    private ICrud<Ubicacion> persUbicacion;

    public ReportesServicios(ICrud<Entrada> persEntr) {
        this.persEntrada = persEntr;
    }

    public Map<Integer, Object[]> reporteEspectaculos(Timestamp desde, Timestamp hasta) throws DatabaseException, RegistroNotFoundExeption {

        List<Entrada> entradas = null;
        Map<Integer, Object[]> resultado = new HashMap<>();

        String sql = "SELECT id, buyer_document, buyer_name, espectaculo_id, ubicacion_id, vendedor_id, soldAt FROM entradas WHERE soldAt BETWEEN ? AND ?";

        try {
            entradas = persEntrada.getList(sql, desde, hasta);

            Map<Integer, Integer> contador = new HashMap<>();
            Map<Integer, Double> total = new HashMap<>();
            Map<Integer, Espectaculo> espectaculos = new HashMap<>();

            for (Entrada entrada : entradas) {
                Integer idEspectaculo = entrada.getEspectaculo().getId();

                // Si no existe el espectaculo, lo cargo
                if (!espectaculos.containsKey(idEspectaculo)) {
                    espectaculos.put(idEspectaculo, entrada.getEspectaculo());
                }

                Double precio = entrada.getUbicacion().getPrecio();

                // Sumo en 1 el contador
                contador.put(idEspectaculo,
                    contador.getOrDefault(idEspectaculo, 0) + 1);

                // Sumo el precio
                total.put(idEspectaculo,
                    total.getOrDefault(idEspectaculo, 0.0) + precio);

            }

            // Agrupo todo en un solo map
            for (Integer id : contador.keySet()) {
                resultado.put(id, new Object[]{
                    espectaculos.get(id),
                    contador.get(id),
                    total.get(id)
                });
            }


        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        
        return resultado;
    }
}
