package nks.abc.domain.view.factory;

import java.util.ArrayList;

import nks.abc.domain.view.object.objects.user.GroupView;
import nks.abc.domain.view.object.objects.user.ParentInfoView;
import nks.abc.domain.view.object.objects.user.StaffView;
import nks.abc.domain.view.object.objects.user.StudentView;

public class UserViewFactory {

	public static StudentView newStudent(){
		StudentView instance = new StudentView();
		instance.setGroups(new ArrayList<GroupView>());
		instance.setParent(new ParentInfoView());
		instance.setIsDeleted(false);
		instance.setMoneyBalance(0);
		return instance;
	}
	public static StaffView newStaff(){
		StaffView instace = new StaffView();
		instace.setIsDeleted(false);
		return instace;
	}
}