package nks.abc.web.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.primefaces.validate.bean.ClientConstraint;

@Target({ METHOD,FIELD,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy=EmailConstraintValidator.class)
@ClientConstraint(resolvedBy=EmailClientValidatorConstraint.class)
@Documented
public @interface Email {
	String message() default "must be an Email";
    
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};           
}
