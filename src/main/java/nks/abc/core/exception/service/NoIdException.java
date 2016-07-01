package nks.abc.core.exception.service;

public class NoIdException extends ServiceException {

	private static final long serialVersionUID = 7425684245254380864L;

	public NoIdException() {
		super();
	}

	public NoIdException(String message) {
		super(message);
	}

	public NoIdException(Throwable cause) {
		super(cause);
	}

	public NoIdException(String message, Throwable cause) {
		super(message, cause);
	}

}
