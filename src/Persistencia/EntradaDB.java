package Persistencia;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidades.Entrada;
import Entidades.Espectaculo;
import Entidades.Ubicacion;
import Entidades.Usuario;
import Excepciones.RegistroNotFoundExeption;

public class EntradaDB extends BaseH2 implements ICrud<Entrada>{

    @Override
    public void save(Entrada entrada) throws SQLException {
		String sql = "INSERT INTO entradas (buyer_document, buyer_name, espectaculo_id, ubicacion_id, vendedor_id, soldAt) VALUES (?,?,?,?,?,?)";
        try {
    		updateDeleteInsertSql(sql, entrada.getDocument(), entrada.getBuyerName(), entrada.getEspectaculo().getId(), entrada.getUbicacion().getId() ,entrada.getVendedor().getId(), entrada.getSoldAt());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Entrada get(Integer id) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Entrada> getList() throws SQLException, IOException, ClassNotFoundException, RegistroNotFoundExeption {
        
        String sql = "SELECT id, buyer_document, buyer_name, espectaculo_id, ubicacion_id, vendedor_id, soldAt FROM entradas";
		ResultSet rs = super.selectSql(sql);
        List<Entrada> entradas = new ArrayList<>();

        while (rs.next()) {

            EspectaculosDB espectaculosDB = new EspectaculosDB();
            Espectaculo espectaculo = espectaculosDB.get(rs.getInt(4));

            UsuarioDB usuarioDB = new UsuarioDB();
            Usuario user = usuarioDB.get(rs.getInt(6));

            UbicacionDB ubicacionDB = new UbicacionDB();
            Ubicacion ubicacion = ubicacionDB.get(rs.getInt(5));

            if (espectaculo != null && user != null && ubicacion != null) {
                entradas.add(new Entrada(rs.getInt(1), rs.getString(2), rs.getString(3), espectaculo, user, rs.getTimestamp(7), ubicacion));
            }
        }
        cerrarConexion();
        return entradas;
    }

    @Override
    public void update(Entrada t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Entrada t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Entrada get(String sql, Object... params) throws IOException, ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Entrada> getList(String sql, Object... params) throws SQLException, IOException, ClassNotFoundException, RegistroNotFoundExeption {
        ResultSet rs = super.selectSql(sql, params);
        List<Entrada> entradas = new ArrayList<>();

        while (rs.next()) {

            EspectaculosDB espectaculosDB = new EspectaculosDB();
            Espectaculo espectaculo = espectaculosDB.get(rs.getInt(4));

            UsuarioDB usuarioDB = new UsuarioDB();
            Usuario user = usuarioDB.get(rs.getInt(6));

            UbicacionDB ubicacionDB = new UbicacionDB();
            Ubicacion ubicacion = ubicacionDB.get(rs.getInt(5));

            if (espectaculo != null && user != null && ubicacion != null) {
                entradas.add(new Entrada(rs.getInt(1), rs.getString(2), rs.getString(3), espectaculo, user, rs.getTimestamp(7), ubicacion));
            }
        }
        cerrarConexion();
        return entradas;
    }

}
