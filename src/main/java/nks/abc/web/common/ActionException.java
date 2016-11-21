package nks.abc.web.common;


public class ActionException extends Exception {

	private static final long serialVersionUID = 2042918848134021191L;

	public ActionException() {
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}

}
