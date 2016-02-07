package nks.abc.service.exception;

public class NoCurrentUserException extends ServiceException {

	private static final long serialVersionUID = 3041295723017752364L;

	public NoCurrentUserException() {
		super();
	}

	public NoCurrentUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoCurrentUserException(String message) {
		super(message);
	}

	public NoCurrentUserException(Throwable cause) {
		super(cause);
	}

}
