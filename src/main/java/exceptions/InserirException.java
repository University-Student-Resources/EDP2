package exceptions;

public class InserirException extends Exception {
	private static final long serialVersionUID = 1L;

	public InserirException() {
		super("No s'ha pogut inserir l'element correctament.");
	}
}
