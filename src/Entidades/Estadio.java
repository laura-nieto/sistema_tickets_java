package Entidades;

public class Estadio {
    
    private Integer id;
    private String name;
    private Integer capacity;
    private String address;
    
    public Estadio (Integer id, String nombre, String direccion, Integer capacidad) {
        this.id       = id;
        this.name     = nombre;
        this.address   = direccion;
        this.capacity = capacidad;
    }

    public Estadio (String nombre, String direccion, Integer capacidad) {
        this.name     = nombre;
        this.address   = direccion;
        this.capacity = capacidad;
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacidad) {
        this.capacity = capacidad;
    }

    public String getAddres() {
        return address;
    }

    public void setAddress(String addres) {
        this.address = addres;
    }
}
