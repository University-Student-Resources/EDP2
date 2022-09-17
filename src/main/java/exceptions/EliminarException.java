package exceptions;

public class EliminarException extends Exception {
	private static final long serialVersionUID = 1L;

	public EliminarException() {
		super("No s'ha pogut eliminar l'element correctament.");
	}
}
