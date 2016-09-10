package nks.abc.bl.view.validation.constrataint;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nks.abc.bl.view.validation.annotation.Email;

public class EmailConstraintValidator implements ConstraintValidator<Email,String> {

	private Pattern pattern;
	private Boolean onlyFilled;
	
	private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\"
			+ "x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")"
			+ "@"
			+ "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
			+ "|"
			+ "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]"
			+ "|"
			+ "[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	@Override
	public void initialize(Email email) {
		onlyFilled = email.notEmpty();
		this.pattern = Pattern.compile(EMAIL_PATTERN);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext cvc) {
		
		//TODO: looks like a bug!
		if(value == null) {
            return true;
		}
		value = value.toLowerCase();
    	if(onlyFilled) {
    		return patternValidation(value);
    	}
		return value.length()==0 || patternValidation(value);
	}
	
	private boolean patternValidation(String value){
		return pattern.matcher(value.toString()).matches();
	}
}
