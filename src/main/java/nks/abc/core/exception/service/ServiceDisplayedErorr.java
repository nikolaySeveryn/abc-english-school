package nks.abc.core.exception.service;

public class ServiceDisplayedErorr extends ServiceException{
	
	private String displayedText;

	public ServiceDisplayedErorr(String displayedText) {
		super();
		this.displayedText = displayedText;
	}

	public ServiceDisplayedErorr(String displayedText, String message, Throwable cause) {
		super(message, cause);
		this.displayedText = displayedText;
	}

	public ServiceDisplayedErorr(String displayedText, String message) {
		super(message);
		this.displayedText = displayedText;
	}

	public ServiceDisplayedErorr(String displayedText, Throwable cause) {
		super(cause);
		this.displayedText = displayedText;
	}
	
	public String getDisplayedText() {
		return displayedText;
	}

}
