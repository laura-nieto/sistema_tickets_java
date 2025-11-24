package Excepciones;

public class EspacioRestanteException extends Exception {

    public EspacioRestanteException() {
        super("Parece que no hay más entradas para la venta");
    }

}
