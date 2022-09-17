package exceptions;

public class BuscarException extends Exception {
	private static final long serialVersionUID = 1L;

	public BuscarException(int nElem) {
		super("No s'ha pogut inserir l'element correctament.\n"
				+ "S'han comprovat "+ nElem + " elements.");
	}
}
