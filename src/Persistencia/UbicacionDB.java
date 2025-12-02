package Persistencia;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidades.Estadio;
import Entidades.Ubicacion;
import Excepciones.RegistroNotFoundExeption;

public class UbicacionDB extends BaseH2 implements ICrud<Ubicacion> {

    @Override
    public Ubicacion get(Integer id) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {

        String sql = "SELECT id, estadio_id, nombre, capacidad, precio FROM estadio_ubicaciones WHERE id = ?";
        ResultSet rs = selectSql(sql, id);

        Ubicacion ub = null;

        if (rs.first()) {

            EstadioDB estadioDb = new EstadioDB();
            Estadio estadio = estadioDb.get(rs.getInt(2));

			ub = new Ubicacion(rs.getInt(1), rs.getString(3), rs.getInt(4), rs.getDouble(5), estadio);
            
        } else {
            throw new RegistroNotFoundExeption();
        }

        cerrarConexion();
        return ub;
    }

    @Override
    public Ubicacion get(String sql, Object... params) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {

		ResultSet rs = super.selectSql(sql, params);

        Ubicacion ub = null;
        
        if (rs.first()) {

            EstadioDB estadioDb = new EstadioDB();
            Estadio estadio = estadioDb.get(rs.getInt(2));

			ub = new Ubicacion(rs.getInt(1), rs.getString(3), rs.getInt(4), rs.getDouble(5), estadio);
            
        } else {
            throw new RegistroNotFoundExeption();
        }

        cerrarConexion();
        return ub;
    }

    @Override
    public List<Ubicacion> getList() throws SQLException, IOException, ClassNotFoundException, RegistroNotFoundExeption {
        
        String sql = "SELECT id, estadio_id, nombre, capacidad, precio FROM estadio_ubicaciones";
		ResultSet rs = super.selectSql(sql);
        List<Ubicacion> ubicaciones = new ArrayList<>();
        
        while (rs.next()) {
            
            EstadioDB estadioDB = new EstadioDB();
            Estadio estadio = estadioDB.get(rs.getInt(2));
            
            ubicaciones.add(new Ubicacion(rs.getInt(1), rs.getString(3), rs.getInt(4), rs.getDouble(5), estadio));
        }
        
        cerrarConexion();
        return ubicaciones;
    }

    @Override
    public List<Ubicacion> getList(String sql, Object... params) throws SQLException, ClassNotFoundException, IOException, RegistroNotFoundExeption {
        
        ResultSet rs = super.selectSql(sql, params);
        List<Ubicacion> ubicaciones = new ArrayList<>();
        
        while (rs.next()) {
            
            EstadioDB estadioDB = new EstadioDB();
            Estadio estadio = estadioDB.get(rs.getInt(2));
            
            ubicaciones.add(new Ubicacion(rs.getInt(1), rs.getString(3), rs.getInt(4), rs.getDouble(5), estadio));
        }
        
        cerrarConexion();
        return ubicaciones;
    }

    @Override
    public void save(Ubicacion ub) throws SQLException {
        String sql = "INSERT INTO estadio_ubicaciones (estadio_id, nombre, capacidad, precio) VALUES (?, ?, ?, ?)";
        updateDeleteInsertSql(sql, ub.getEstadio().getId(), ub.getNombre(), ub.getCapacidad(), ub.getPrecio());
    }

    @Override
    public void update(Ubicacion ub) throws SQLException {
        String sql = "UPDATE estadio_ubicaciones SET estadio_id=?, nombre=?, capacidad=?, precio=? WHERE id = ?";
        updateDeleteInsertSql(sql, ub.getEstadio().getId(), ub.getNombre(), ub.getCapacidad(), ub.getPrecio(), ub.getId());
    }

    @Override
    public void delete(Ubicacion ub) throws SQLException {
        String sql = "DELETE FROM estadio_ubicaciones WHERE id = ?";
        updateDeleteInsertSql(sql, ub.getId());
    }
}
