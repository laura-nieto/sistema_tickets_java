package Entidades;

public class Ubicacion {
    private Integer id;
    private String nombre;
    private Integer capacidad;
    private Double precio;
    private Estadio estadio;

    public Ubicacion(String nombre, Integer capacidad, Double precio, Estadio estadio) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precio = precio;
        this.estadio = estadio;
    }

    public Ubicacion(Integer id, String nombre, Integer capacidad, Double precio, Estadio estadio) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precio = precio;
        this.estadio = estadio;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Estadio getEstadio(){
        return estadio;
    }

    public void setEstadio(Estadio est) {
        this.estadio = est;
    }

}
