import java.sql.Timestamp;

public class App {
    public static void main(String[] args) throws Exception {
        
        Administrador admin = new Administrador("Pedro", "Juarez", "pjuarez", "123455");
        Vendedor vende = new Vendedor("Roberto","Perez","rperez","123456");

        //admin.login("pjuarez", "123455");
        Estadio est = admin.crearEstadio("River", "Pedro 123", 10);
    
        Espectaculo esp1 = admin.crearEspectaculo("Champagne las pone mimosas", est, Timestamp.valueOf("2025-10-27 10:30:00"), 20.2);
        Espectaculo esp2 = admin.crearEspectaculo("El gran chef", est, Timestamp.valueOf("2025-11-27 10:30:00"), 10.5);

        System.out.println("Nombre del espectaculo: " + esp1.getName());
        System.out.println("Estadio: " + esp1.getEstadio().getName());
    
    }
}
