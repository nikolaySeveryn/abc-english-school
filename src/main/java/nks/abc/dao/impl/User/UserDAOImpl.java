package nks.abc.dao.impl.User;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import nks.abc.dao.exception.DAOException;
import nks.abc.dao.impl.DAO;
import nks.abc.dao.user.UserDAO;
import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;

@Repository
public class UserDAOImpl extends DAO implements UserDAO {

	public UserDAOImpl() {
		super(User.class);
	}

	@Override
	public User findUserByLogin(String login) {
		try{
			// Expects that logins is unique
			List<Staff> list = getCriteria().add(Restrictions.eq("login", login)).setMaxResults(1).list();
			if (list.size() < 1) {
				return null;
			}
			return list.get(0);
		}
		catch (HibernateException he){
			throw new DAOException("Error of the user finding", he);
		}
	}

}
