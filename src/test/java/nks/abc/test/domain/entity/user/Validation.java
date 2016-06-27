package nks.abc.test.domain.entity.user;

import static org.junit.Assert.*;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import nks.abc.domain.view.object.objects.user.StaffView;

@Component
public class Validation {
	
	private javax.validation.ValidatorFactory validatorFactory;
	
	@Before
	public void init(){
		System.out.println("init");
		validatorFactory = javax.validation.Validation.buildDefaultValidatorFactory();
		System.out.println("factory: " + validatorFactory);
	}

	@Test
	public void test() {
//		Validator validator = validatorFactory.getValidator();
//		StaffView staff = new StaffView();
//		validator.validate(staff);
	}

}
