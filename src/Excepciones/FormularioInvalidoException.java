package Excepciones;

public class FormularioInvalidoException extends Exception{

	public FormularioInvalidoException() {
		super("Los datos son invalidos");
	}
}
