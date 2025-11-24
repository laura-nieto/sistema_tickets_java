package Entidades;

import java.sql.Timestamp;
import java.util.List;

public class Espectaculo {
    private Integer id;
    private String name;
    private Estadio estadio;
    private Timestamp timestamp;
    private Double price;
    private List<Entrada> entradas;

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

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public Integer espacioRestante() {

        Integer cantEntradas = this.entradas.size();

        Integer espacio = this.estadio.getCapacity();

        return espacio - cantEntradas;
    }

}
