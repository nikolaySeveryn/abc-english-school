package nks.abc.domain.exception;


public class VerificationException extends DomainException {

	private static final long serialVersionUID = 5491781095480606260L;

	public VerificationException() {
	}

	public VerificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public VerificationException(String message) {
		super(message);
	}

	public VerificationException(Throwable cause) {
		super(cause);
	}

}
