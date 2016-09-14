package nks.abc.domain.validation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.primefaces.validate.bean.ClientConstraint;

import nks.abc.domain.validation.client.EmailClientValidator;
import nks.abc.domain.validation.constrataint.EmailConstraintValidator;

@Target({ METHOD,FIELD,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy=EmailConstraintValidator.class)
@ClientConstraint(resolvedBy=EmailClientValidator.class)
public @interface Email {
	String message() default "must be an Email";
	boolean notEmpty() default true;
    
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}