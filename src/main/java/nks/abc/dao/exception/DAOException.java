package nks.abc.dao.exception;

public class DAOException extends RuntimeException{

	private static final long serialVersionUID = -6961815001985918928L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

}
