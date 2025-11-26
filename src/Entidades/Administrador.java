package Entidades;

public class Administrador extends Usuario {

    public Administrador(Integer id, String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public Administrador(String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public Administrador(Integer id, String nombre, String apellido, String doc, String usuario, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, isAdmin);
    }

}
