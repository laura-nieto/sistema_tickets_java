package Excepciones;

public class UsuarioExistenteException extends Exception {

    public UsuarioExistenteException() {
        super("Ya existe el usuario.");
    }
}
