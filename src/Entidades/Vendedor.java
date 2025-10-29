package Entidades;
public class Vendedor extends Usuario {

    public Vendedor(Integer id, String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public void vender(String nombre, String apellido, Espectaculo espect, Integer cantidad) {
        // verificar que haya la cantidad de espacios para el espectaculo
        // tengo que acceder a espectaculo - estadio.capacity y comparar
        
        // new ticket?
    }
}
