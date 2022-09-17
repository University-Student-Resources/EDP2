package exceptions;

public class CrearException extends Exception {
    private static final long serialVersionUID = 1L;

    public CrearException() {
        super("No s'ha pogut crear la llista correctament.");
    }
}
