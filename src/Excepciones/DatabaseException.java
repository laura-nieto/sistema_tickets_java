package Excepciones;

public class DatabaseException extends Exception {

    public DatabaseException() {
        super("Error en la base de datos");
    }

}
