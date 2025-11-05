package Persistencia;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidades.Espectaculo;
import Entidades.Estadio;
import Excepciones.DatabaseException;
import Excepciones.RegistroNotFoundExeption;
import Servicio.EstadioServicio;

public class EspectaculosDB extends BaseH2 implements ICrud<Espectaculo> {

    @Override
    public void save(Espectaculo t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Espectaculo get(Integer id)
            throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Espectaculo get(String sql, String value) throws IOException, ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Espectaculo> get() throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT id, name, estadio_id, timestamp, price FROM espectaculos";
		ResultSet rs = super.selectSql(sql);
		List<Espectaculo> espectaculos = new ArrayList<>();
		while (rs.next()) {

            EstadioServicio estadioServicio = new EstadioServicio(new EstadioDB());
            Estadio estadio = estadioServicio.get(rs.getInt(3));

            if (estadio != null) {
                espectaculos.add(new Espectaculo(rs.getInt(1), rs.getString(2), estadio, rs.getTimestamp(4) , rs.getDouble(5)));
            }
		}
		cerrarConexion();
		return espectaculos;
    }

    @Override
    public void update(Espectaculo t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Espectaculo t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
