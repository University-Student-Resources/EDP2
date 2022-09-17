package exceptions;

public class ForaRangException extends Exception {
	private static final long serialVersionUID = 1L;

	public ForaRangException() {
		super("Index/posicio fora de rang.");
	}
}
