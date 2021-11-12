package kancho.realestate.comparingprices.exception;

public class DuplicateLoginException extends RuntimeException {
	public DuplicateLoginException(String message) {
		super(message);
	}
}