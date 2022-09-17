package exceptions;

public class ObtenirException extends Exception {
	private static final long serialVersionUID = 1L;

	public ObtenirException() {
		super("No s'ha pogut obtenir l'element correctament.");
	}
}
