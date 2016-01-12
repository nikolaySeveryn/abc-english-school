package nks.abc.test.domain.entity.user;

import static org.junit.Assert.*;
import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;

import org.junit.Test;

public class UserTest {

	@Test
	public void testPasswordEncript() {
		User user = new Staff();
		user.updatePassword("passwordTest");
		assertEquals(user.getPasswordHash(), "iZtuqJwgMU1Fd2HsZBhsRg==");
		user.updatePassword("otherLongVerryLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongerPassword");
		assertEquals(user.getPasswordHash(), "rEbqf+zg/8r+AFR3mJc2Sg==");
		
		user.updatePassword("goniagonia");
		assertEquals(user.getPasswordHash(), "dxoYGaCOIVvIMPHMlf3aSw==");
	}

}
