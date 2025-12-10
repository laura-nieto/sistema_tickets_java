package Servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Entidades.Administrador;
import Entidades.Usuario;
import Entidades.Vendedor;
import Excepciones.DatabaseException;
import Excepciones.RegistroNotFoundExeption;
import Excepciones.UsuarioExistenteException;
import Persistencia.ICrud;

public class UsuarioServicio {

    private ICrud<Usuario> persistencia;
	
	public UsuarioServicio(ICrud<Usuario> persistencia) {
		this.persistencia = persistencia;
	}

    public Usuario get(Integer id) {

        Usuario user = null;

        try {
            user = persistencia.get(id);
        } catch (ClassNotFoundException | IOException | SQLException | RegistroNotFoundExeption e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<Usuario> list() throws DatabaseException, RegistroNotFoundExeption {
        List<Usuario> usuarios = null;

        try {
            usuarios = persistencia.getList();
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return usuarios;
    }

    public void insert(String nombre, String apellido, String documento, String username, String password, Boolean isAdmin) throws DatabaseException, UsuarioExistenteException, RegistroNotFoundExeption {
        
        Usuario usuario = null;
        
        String sql = "SELECT id, name, lastname, document, username, password, isAdmin FROM usuarios WHERE username = ?";
        
        try {
            
            // Try catch para que si lanza RegistroNotFoundExeption continue la ejecucion
            // Valido Username
            try {
                Usuario existUsername = persistencia.get("SELECT id, name, lastname, document, username, password, isAdmin FROM usuarios WHERE username = ?", username);

                if (existUsername != null) {
                    throw new UsuarioExistenteException();
                }

            } catch (RegistroNotFoundExeption e) {
                System.out.println("El usuario con username no existe, continuo creacion");
            }

            // Valido Documento
            try {
                Usuario existDoc = persistencia.get("SELECT id, name, lastname, document, username, password, isAdmin FROM usuarios WHERE document = ?", documento);

                if (existDoc != null) {
                    throw new UsuarioExistenteException();
                }

            } catch (RegistroNotFoundExeption e) {
                System.out.println("El usuario con documento no existe, continuo creacion");
            }


            if (isAdmin) {
                usuario = new Administrador(nombre, apellido, documento, username, password, isAdmin);
            } else {
                usuario = new Vendedor(nombre, apellido, documento, username, password, isAdmin);
            }

            persistencia.save(usuario);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void edit(Integer id, String nombre, String apellido, String documento, String username, String password, Boolean isAdmin) throws DatabaseException, RegistroNotFoundExeption {
        try {
            Usuario user = persistencia.get(id);

            user.setName(nombre);
            user.setLastname(apellido);
            user.setDocument(documento);
            user.setUsername(username);
            user.setIsAdmin(isAdmin);

            if (password.isEmpty()) {
                user.setPassword(user.getPassword());
            } else {
                user.setPassword(password);
            }

            persistencia.update(user);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void delete(Integer id) throws RegistroNotFoundExeption, DatabaseException {
        try {
            Usuario usuario = persistencia.get(id);
            persistencia.delete(usuario);

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }
}
