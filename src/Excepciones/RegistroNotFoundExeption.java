package Excepciones;

public class RegistroNotFoundExeption extends Exception {

    public RegistroNotFoundExeption () {
        super("No existe registro");
    }
}
