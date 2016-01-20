package nks.abc.dao.impl.User;


import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import nks.abc.dao.impl.BaseDAOImpl;
import nks.abc.dao.user.StaffDAO;
import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;

@Repository
public class StaffDAOImpl extends BaseDAOImpl<Staff, Long> implements StaffDAO {

	public StaffDAOImpl() {
		super(Staff.class);
	}

	@Override
	public Staff findUserByLogin(String login) {
		//Expects that logins is unique
		List<Staff> list = getCriteria().add(Restrictions.eq("login", login)).setMaxResults(1).list();
		if(list.size()<1){
			return null;
		}
		return list.get(0);
	}


}
