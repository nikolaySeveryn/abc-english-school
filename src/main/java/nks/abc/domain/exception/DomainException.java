package nks.abc.domain.exception;

public class DomainException extends RuntimeException{

	private static final long serialVersionUID = 1886335167779010589L;

	public DomainException() {
		super();
	}

	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}

	public DomainException(String message) {
		super(message);
	}

	public DomainException(Throwable cause) {
		super(cause);
	}

}
