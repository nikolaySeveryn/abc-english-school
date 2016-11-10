package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.user.User;


@Repository
public class UserRepositoty extends BaseRepositoryImpl<User> {

	public UserRepositoty() {
		super(User.class);
	}

}
