package nks.abc.test.domain.entity.user;

import static org.junit.Assert.*;

import org.junit.Test;

import nks.abc.domain.school.Group;
import nks.abc.domain.school.impl.GroupImpl;

public class GroupTest {
	
	private Group group = new GroupImpl();
	private static final double delta = 0.0000000000001;

	@Test
	public void toFloatTest() {
		group.setTarif(56254);
		assertEquals(562.54, group.getFloatTarif(), delta);
		
		group.setTarif(56200);
		assertEquals(562d, group.getFloatTarif(), delta);
		
		group.setTarif(523220);
		assertEquals(5232.20d, group.getFloatTarif(), delta);
	}
	
	public void fromFloatTest(){
		group.setFloatTarif(125.32);
		assertEquals(new Integer(12532), group.getTarif());
		
		group.setFloatTarif(125d);
		assertEquals(new Integer(12500), group.getTarif());
		
		group.setFloatTarif(125.3);
		assertEquals(new Integer(12530), group.getTarif());
	}

}
