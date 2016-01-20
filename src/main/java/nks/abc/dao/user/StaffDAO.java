package nks.abc.dao.user;


import nks.abc.dao.BaseDAO;
import nks.abc.domain.entity.user.Staff;

public interface StaffDAO extends BaseDAO<Staff, Long> {
	Staff findUserByLogin(String login);
}
