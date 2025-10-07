public class Espectaculo {
    private String name;
    private Estadio estadio;

    public Espectaculo (String nombre, Estadio estadio) {
        this.name    = nombre;
        this.estadio = estadio;
    }

    public String getName() {
        return name;
    }

    public Estadio getEstadio() {
        return estadio;
    }
}
