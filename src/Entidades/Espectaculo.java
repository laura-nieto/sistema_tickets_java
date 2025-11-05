package Entidades;
import java.sql.Timestamp;

public class Espectaculo {
    private Integer id;
    private String name;
    private Estadio estadio;
    private Timestamp timestamp;
    private Double price;
    // lista de tickets? 

    public Espectaculo (String nombre, Estadio estadio, Timestamp fechaHora, double precio) {
        this.name    = nombre;
        this.estadio = estadio;
        this.timestamp = fechaHora;
        this.price = precio;
    }

    public Espectaculo (Integer id, String nombre, Estadio estadio, Timestamp fechaHora, double precio) {
        this.id = id;
        this.name    = nombre;
        this.estadio = estadio;
        this.timestamp = fechaHora;
        this.price = precio;
    }

    public Integer getId() {
        return id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
