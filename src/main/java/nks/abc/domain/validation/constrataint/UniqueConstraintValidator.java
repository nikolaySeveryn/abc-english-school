package nks.abc.domain.validation.constrataint;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import nks.abc.dao.base.interfaces.UniqueChecker;
import nks.abc.domain.validation.annotation.Unique;

@Component
public class UniqueConstraintValidator implements ConstraintValidator<Unique,Object>{

	private Unique constraintAnnotation;
	
	private static ApplicationContext context;
	
	private UniqueChecker uniqueChecker;
	
	@Autowired
	public void setApplicationContext(ApplicationContext context){
		this.context = context;
	}
	
	@Override
	public void initialize(Unique constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
		uniqueChecker = (UniqueChecker) context.getBean(UniqueChecker.class);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		synchronized (this) {
			if(value == null) {
				return false;
			}
			return uniqueChecker.isUnique(constraintAnnotation.entity(), constraintAnnotation.field(), value);
		}
	}

}
