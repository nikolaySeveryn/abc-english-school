package nks.abc.domain.exception;


public class CrudException extends DomainException {

	private static final long serialVersionUID = 1686967687342661932L;

	public CrudException() {
	}

	public CrudException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrudException(String message) {
		super(message);
	}

	public CrudException(Throwable cause) {
		super(cause);
	}

}
