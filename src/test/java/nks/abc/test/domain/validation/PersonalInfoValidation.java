package nks.abc.test.domain.validation;

import static org.junit.Assert.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import nks.abc.domain.user.PersonalInfo;
import nks.abc.domain.user.factory.AccountFactory;

public class PersonalInfoValidation {

//	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		validator = factory.getValidator();
	}

	@Test
	public void test() {
//		PersonalInfo instance = AccountFactory.createPersonalInfo();
//		validator.validate(instance);
		
	}

}
