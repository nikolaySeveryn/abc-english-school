package nks.abc.domain.user.factory;

import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.impl.AdministratorImpl;
import nks.abc.domain.user.impl.TeacherImpl;

public class UserFactory {

	public static Teacher createTeacher(){
		return new TeacherImpl();
	}
	public static Administrator createAdministrator(){
		return new AdministratorImpl();
	}
}
