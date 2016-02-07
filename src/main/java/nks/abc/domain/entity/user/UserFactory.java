package nks.abc.domain.entity.user;

public class UserFactory {

	public static Teacher createTeacher(){
		return new Teacher();
	}
	public static Administrator createAdministrator(){
		return new Administrator();
	}
}
