package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.user.User;
import nks.abc.domain.user.impl.UserImpl;


@Repository
public class UserRepositoty extends BaseRepositoryImpl<User> {

	public UserRepositoty() {
		super(UserImpl.class);
	}

}
