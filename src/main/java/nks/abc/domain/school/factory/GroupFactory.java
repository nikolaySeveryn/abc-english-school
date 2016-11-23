package nks.abc.domain.school.factory;

import nks.abc.domain.school.Group;
import nks.abc.domain.school.impl.GroupImpl;

public class GroupFactory {

	public static Group createGroup(){
		return new GroupImpl();
	}

}
