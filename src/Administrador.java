import java.sql.Timestamp;

public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String usuario, String contrasena) {
        super(nombre, apellido, usuario, contrasena);
    }
    
    public void crearUsuario(String nombre, String apellido, String usuario, String contrasena, boolean isAdmin) {
        if (isAdmin) {
            Administrador user = new Administrador(nombre, apellido, usuario, contrasena);
        } else {
            Vendedor user = new Vendedor(nombre, apellido, usuario, contrasena);
        }
    }

    public Estadio crearEstadio(String nombre, String direccion, Integer capacidad) {
        Estadio est = new Estadio(nombre, direccion, capacidad);
        return est;
    }

    public Espectaculo crearEspectaculo(String nombre, Estadio estadio, Timestamp fecha, double precio) {
        Espectaculo esp = new Espectaculo(nombre, estadio, fecha, precio);
        return esp;
    }
}
