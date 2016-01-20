package nks.abc.dao.user;

import nks.abc.domain.entity.user.User;

public interface UserDAO {
	User findUserByLogin(String login);
}
