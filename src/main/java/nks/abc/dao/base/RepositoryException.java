package nks.abc.dao.base;

public class RepositoryException extends RuntimeException{

	private static final long serialVersionUID = -6961815001985918928L;

	public RepositoryException() {
		super();
	}

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepositoryException(String message) {
		super(message);
	}

	public RepositoryException(Throwable cause) {
		super(cause);
	}

}
