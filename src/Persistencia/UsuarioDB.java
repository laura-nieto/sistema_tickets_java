package Persistencia;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidades.Administrador;
import Entidades.Usuario;
import Entidades.Vendedor;
import Excepciones.RegistroNotFoundExeption;

public class UsuarioDB extends BaseH2 implements ICrud<Usuario>{

    @Override
    public void save(Usuario user) throws SQLException {
		String sql = "INSERT INTO usuarios (name, lastname, document, username, password, isAdmin) VALUES (?,?,?,?,?,?)";
        try {
    		updateDeleteInsertSql(sql, user.getName(), user.getLastname(), user.getDocument(), user.getUsername(), user.getPassword(), user.getIsAdmin());        
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Usuario get(Integer id) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {
		String sql = "SELECT id, name, lastname, document, username, password, isAdmin FROM usuarios WHERE id = ?";
		ResultSet rs = selectSql(sql, id);

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
			throw new RegistroNotFoundExeption();
		}
    }

    @Override
    public List<Usuario> get() throws SQLException {
		String sql = "SELECT id, name, lastname, document, username, isAdmin FROM usuarios";
		ResultSet rs = super.selectSql(sql);
		List<Usuario> usuarios = new ArrayList<>();
		while (rs.next()) {
            if (rs.getBoolean(6)) {
                usuarios.add(new Administrador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6)));
            } else {
                usuarios.add(new Vendedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6)));
            }
		}
		cerrarConexion();
		return usuarios;
    }

    @Override
    public void update(Usuario user) throws SQLException {
		String sql = "UPDATE usuarios SET name=?, lastname=?, document=?, username=?, password=?, isAdmin=? WHERE id = ?"; 
		try {
			Integer updated = updateDeleteInsertSql(sql, user.getName(), user.getLastname(), user.getDocument(), user.getUsername(), user.getPassword(), user.getIsAdmin(), user.getId());
            if (updated != 1) {
                throw new SQLException();
            }
		} catch (SQLException e) {
			throw e;
		}
    }

    @Override
    public void delete(Usuario user) throws SQLException {
		String sql = "DELETE FROM usuarios WHERE id = ?"; 
		try {
			Integer updated = updateDeleteInsertSql(sql, user.getId());
            if (updated != 1) {
                throw new SQLException();
            }
		} catch (SQLException e) {
			throw e;
		}
    }

    @Override
    public Usuario get(String sql, String value) throws IOException, ClassNotFoundException, SQLException {

		ResultSet rs = selectSql(sql, value);

        Usuario u = null;

		if (rs.first()) {
            if (rs.getBoolean(7)) {
                u = new Administrador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
            } else {
                u = new Vendedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
            }
		}

        cerrarConexion();
		return u;
    }

}
