package nks.abc.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import nks.abc.dao.UserDAO;
import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;

@Repository
public class UserDAOImpl extends BaseDAOImpl<User, Long> implements UserDAO {

	public UserDAOImpl() {
		super(User.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByLogin(String login) {
		//Expected that logins is unique
		List<Staff> list = getCriteria().add(Restrictions.eq("login", login)).setMaxResults(1).list();
		if(list.size()<1){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<Staff> getAllStaff() {
		Criteria crit = getSession().createCriteria(Staff.class);
		return crit.list();
	}
	
	@Override
	public Staff findStaffById(Long id){
		return (Staff) getSession().get(Staff.class, id);
	}
}
