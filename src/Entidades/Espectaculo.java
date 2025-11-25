package Entidades;

import java.sql.Timestamp;
import java.util.List;

public class Espectaculo {
    private Integer id;
    private String name;
    private Estadio estadio;
    private Timestamp timestamp;
    private List<Entrada> entradas;

    public Espectaculo (String nombre, Estadio estadio, Timestamp fechaHora) {
        this.name    = nombre;
        this.estadio = estadio;
        this.timestamp = fechaHora;
    }

    public Espectaculo (Integer id, String nombre, Estadio estadio, Timestamp fechaHora) {
        this.id = id;
        this.name    = nombre;
        this.estadio = estadio;
        this.timestamp = fechaHora;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
