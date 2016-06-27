package nks.abc.domain.view.validation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.primefaces.validate.bean.ClientConstraint;

import nks.abc.domain.view.validation.client.UniqueClientValidator;
import nks.abc.domain.view.validation.constrataint.UniqueConstraintValidator;


@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=UniqueConstraintValidator.class)
@ClientConstraint(resolvedBy=UniqueClientValidator.class)
public @interface Unique {
	String message() default "Allready exist";
	Class<?> entity();
	String field();
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
