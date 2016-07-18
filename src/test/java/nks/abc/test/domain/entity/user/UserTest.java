package nks.abc.test.domain.entity.user;

import static org.junit.Assert.*;

import org.junit.Test;

import nks.abc.bl.domain.user.Account;

public class UserTest {

	@Test
	public void testPasswordEncript() {
		Account user = new Account();
		user.updatePassword("passwordTest");
		assertEquals(user.getPasswordHash(), "iZtuqJwgMU1Fd2HsZBhsRg==");
		user.updatePassword("otherLongVerryLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongerPassword");
		assertEquals(user.getPasswordHash(), "rEbqf+zg/8r+AFR3mJc2Sg==");
		
		user.updatePassword("goniagonia");
		assertEquals(user.getPasswordHash(), "dxoYGaCOIVvIMPHMlf3aSw==");
	}

}
