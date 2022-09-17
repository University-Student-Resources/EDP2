package exceptions;

public class ExistentException extends Exception {
    private static final long serialVersionUID = 1L;

    public ExistentException() {
        super("No s'ha pogut trobar l'element correctament, no existeix connexió.");
    }

}
