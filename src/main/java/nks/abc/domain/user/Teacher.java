package nks.abc.domain.user;

import java.util.List;

import nks.abc.domain.school.Group;

public interface Teacher extends Staff{
	
	List<Group>retreiveGroups();
	Long countOfGroup();

}