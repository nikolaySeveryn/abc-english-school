package nks.abc.domain.user.factory;

import java.util.HashSet;

import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.Parent;
import nks.abc.domain.user.Student;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.impl.AdministratorImpl;
import nks.abc.domain.user.impl.GroupImpl;
import nks.abc.domain.user.impl.ParentImpl;
import nks.abc.domain.user.impl.PersonalInfoImpl;
import nks.abc.domain.user.impl.StudentImpl;
import nks.abc.domain.user.impl.TeacherImpl;

public class UserFactory {

	public static Teacher createTeacher(){
		return new TeacherImpl();
	}
	public static Administrator createAdministrator(){
		Administrator instatnce = new AdministratorImpl();
		instatnce.setAccount(AccountFactory.createAccount());
		return instatnce;
	}

	public static Student createStudent() {
		Student instance = new StudentImpl();
		instance.setGroups(new HashSet<GroupImpl>());
		instance.setAccount(AccountFactory.createAccount());
		instance.setParent(createParent());
		instance.getAccount().setIsStudent(true);
		return instance;
	}
	
	public static Parent createParent(){
		Parent instance = new ParentImpl();
		instance.setPersonalInfo(new PersonalInfoImpl());
		return instance;
	}
	
	
}
