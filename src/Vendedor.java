public class Vendedor extends Usuario {

    public Vendedor(String nombre, String apellido, String usuario, String contrasena) {
        super(nombre, apellido, usuario, contrasena);
    }

    public void vender(String nombre, String apellido, Espectaculo espect, Integer cantidad) {
        // verificar que haya la cantidad de espacios para el espectaculo
        // tengo que acceder a espectaculo - estadio.capacity y comparar
        
        // new ticket?
    }
}
