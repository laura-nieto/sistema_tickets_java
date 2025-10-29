package Servicio;

import java.io.IOException;
import java.sql.SQLException;

import Entidades.Usuario;
import Excepciones.DatabaseException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.ICrud;

public class LoginServicio {
	
    private ICrud<Usuario> persistencia;
	
	public LoginServicio(ICrud<Usuario> persistencia) {
		this.persistencia = persistencia;
	}

    public Usuario login(String username, String password) throws RegistroNotFoundExeption, DatabaseException {
        
        Usuario user = null;

        try {
            user = persistencia.get("username", username);

            if (!password.equals(user.getPassword())) {
                throw new RegistroNotFoundExeption();
            }

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        } catch (RegistroNotFoundExeption e) {
            throw e;
        }

        return user;
    }
}
