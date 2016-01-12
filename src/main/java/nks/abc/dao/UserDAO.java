package nks.abc.dao;

import java.util.List;

import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;

public interface UserDAO extends BaseDAO<User, Long> {
	
	User findUserByLogin(String login);
	List<Staff> getAllStaff();
	Staff findStaffById (Long id);

}
