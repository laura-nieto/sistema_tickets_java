package Persistencia;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidades.Estadio;
import Excepciones.RegistroNotFoundExeption;

public class EstadioDB extends BaseH2 implements ICrud<Estadio>{

    @Override
    public void save(Estadio estadio) throws IOException, SQLException {
		String sql = "INSERT INTO ESTADIOS (NAME, CAPACITY, ADDRESS) VALUES (?,?,?)";

		updateDeleteInsertSql(sql, estadio.getName(), estadio.getCapacity(), estadio.getAddres());
    }

    @Override
    public Estadio get(Integer id) throws IOException, ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Estadio get(String column, String value) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Estadio> get() throws IOException, ClassNotFoundException, SQLException {
		String sql = "SELECT ID, NAME, ADDRESS, CAPACITY FROM ESTADIOS";
		ResultSet rs = super.selectSql(sql);
		List<Estadio> estadios = new ArrayList<>();
		while (rs.next()) {
			estadios.add(new Estadio(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
		}
		cerrarConexion();
		return estadios;
    }

    @Override
    public void update(Estadio t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Estadio t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
