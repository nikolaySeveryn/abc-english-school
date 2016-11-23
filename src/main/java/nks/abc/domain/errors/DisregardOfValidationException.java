package nks.abc.domain.errors;


public class DisregardOfValidationException extends RuntimeException {

	private static final long serialVersionUID = 8544509654052986791L;

	public DisregardOfValidationException() {
	}
	
	public DisregardOfValidationException(ErrorsSet<?>errors){
		super(errors.toString());
	}
}
