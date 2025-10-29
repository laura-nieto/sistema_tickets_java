package Entidades;

public class Administrador extends Usuario {

    public Administrador(Integer id, String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, contrasena, isAdmin);
    }
    
    /*public void crearUsuario(String nombre, String apellido, String usuario, String contrasena, String doc , boolean isAdmin) {
        if (isAdmin) {
            Administrador user = new Administrador(nombre, apellido, usuario, contrasena, doc);
        } else {
            Vendedor user = new Vendedor(nombre, apellido, usuario, contrasena, doc);
        }
    }

    public Estadio crearEstadio(String nombre, String direccion, Integer capacidad) {
        Estadio est = new Estadio(nombre, direccion, capacidad);
        return est;
    }

    public Espectaculo crearEspectaculo(String nombre, Estadio estadio, Timestamp fecha, double precio) {
        Espectaculo esp = new Espectaculo(nombre, estadio, fecha, precio);
        return esp;
    }*/
}
