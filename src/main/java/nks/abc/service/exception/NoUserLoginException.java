package nks.abc.service.exception;

public class NoUserLoginException extends ServiceException {

	private static final long serialVersionUID = 8846933621619411532L;

	public NoUserLoginException() {
		super();
	}

	public NoUserLoginException(String message) {
		super(message);
	}

	public NoUserLoginException(Throwable cause) {
		super(cause);
	}

	public NoUserLoginException(String message, Throwable cause) {
		super(message, cause);
	}

}
