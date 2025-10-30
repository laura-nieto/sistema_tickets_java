package Persistencia;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Entidades.Administrador;
import Entidades.Usuario;
import Entidades.Vendedor;
import Excepciones.RegistroNotFoundExeption;

public class UsuarioDB extends BaseH2 implements ICrud<Usuario>{

    @Override
    public void save(Usuario user) throws IOException, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Usuario get(Integer id) throws IOException, ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Usuario> get() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public void update(Usuario user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Usuario user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Usuario get(String sql, String value) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {

		ResultSet rs = selectSql(sql, value);

        Usuario u = null;

		if (rs.first()) {
            if (rs.getBoolean(6)) {
                u = new Administrador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
            } else {
                u = new Vendedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
            }
			cerrarConexion();
			return u;
		} else {
			cerrarConexion();
			throw new RegistroNotFoundExeption();
		}
    }

}
