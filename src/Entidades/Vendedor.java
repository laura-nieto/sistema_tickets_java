package Entidades;
public class Vendedor extends Usuario {

    public Vendedor(Integer id, String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public Vendedor(String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public Vendedor(Integer id, String nombre, String apellido, String doc, String usuario, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, isAdmin);
    }

}
