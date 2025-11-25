package Persistencia;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidades.Estadio;
import Entidades.Ubicacion;
import Excepciones.RegistroNotFoundExeption;

public class EstadioDB extends BaseH2 implements ICrud<Estadio>{

    @Override
    public void save(Estadio estadio) throws SQLException {
		String sql = "INSERT INTO estadios (name, capacity, address) VALUES (?,?,?)";
        try {
    		updateDeleteInsertSql(sql, estadio.getName(), estadio.getCapacity(), estadio.getAddres());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Estadio get(Integer id) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {
		String sql = "SELECT id, name, address, capacity FROM estadios WHERE id = ?";
		ResultSet rs = selectSql(sql, id);
		if (rs.first()) {
			Estadio est = new Estadio(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
			
			// Cargo ubicaciones del estadio
			String sqlUb = "SELECT id, nombre, capacidad, precio FROM estadio_ubicaciones WHERE estadio_id = ?";
			ResultSet rsUb = selectSql(sqlUb, id);

			List<Ubicacion> ubicaciones = new ArrayList<>();
			while (rsUb.next()) {
				ubicaciones.add(new Ubicacion(rsUb.getInt(1), rsUb.getString(2), rsUb.getInt(3), rsUb.getDouble(4), est));
			}

        	est.setUbicaciones(ubicaciones);
			
			cerrarConexion();
			return est;
		} else {
			throw new RegistroNotFoundExeption();
		}
    }

    @Override
    public List<Estadio> getList() throws IOException, ClassNotFoundException, SQLException {
		String sql = "SELECT id, name, address, capacity FROM estadios";
		ResultSet rs = super.selectSql(sql);
		List<Estadio> estadios = new ArrayList<>();
		while (rs.next()) {
			estadios.add(new Estadio(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
		}
		cerrarConexion();
		return estadios;
    }

    @Override
    public void update(Estadio estadio) throws SQLException {
		String sql = "UPDATE estadios SET name=?, capacity=?, address=? WHERE id = ?"; 
		try {
			Integer updated = updateDeleteInsertSql(sql, estadio.getName(), estadio.getCapacity(), estadio.getAddres(), estadio.getId());
            if (updated != 1) {
                throw new SQLException();
            }
		} catch (SQLException e) {
			throw e;
		}
    }

    @Override
    public void delete(Estadio estadio) throws SQLException {
		String sql = "DELETE FROM estadios WHERE id = ?"; 
		try {
			Integer updated = updateDeleteInsertSql(sql, estadio.getId());
            if (updated != 1) {
                throw new SQLException();
            }
		} catch (SQLException e) {
			throw e;
		}
    }

	@Override
	public Estadio get(String sql, Object... params) throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}

	@Override
	public List<Estadio> getList(String sql, Object... params)
			throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getList'");
	}

}
