package nks.abc.core.exception.service;


public class SendMailException extends ServiceException {

	private static final long serialVersionUID = 2371028013592741312L;

	public SendMailException() {
		super();
	}

	public SendMailException(String message, Throwable cause) {
		super(message, cause);
	}

	public SendMailException(String message) {
		super(message);
	}

	public SendMailException(Throwable cause) {
		super(cause);
	}

}
