package Entidades;
import java.sql.Timestamp;

public class Espectaculo {
    private String name;
    private Estadio estadio;
    private Timestamp timestamp;
    private double price;
    // lista de tickets? 

    public Espectaculo (String nombre, Estadio estadio, Timestamp fechaHora, double precio) {
        this.name    = nombre;
        this.estadio = estadio;
        this.timestamp = fechaHora;
        this.price = precio;
    }

    public String getName() {
        return name;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }
}
