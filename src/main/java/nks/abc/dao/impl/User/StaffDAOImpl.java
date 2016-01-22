package nks.abc.dao.impl.User;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import nks.abc.dao.exception.DAOException;
import nks.abc.dao.impl.BaseDAOImpl;
import nks.abc.dao.user.StaffDAO;
import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;

@Repository
public class StaffDAOImpl extends BaseDAOImpl<Staff, Long> implements StaffDAO {
	
	private final static Logger log = Logger.getLogger(StaffDAOImpl.class);

	public StaffDAOImpl() {
		super(Staff.class);
	}

	@Override
	public Staff findStaffByLogin(String login) {
		try{
			//Expects that logins is unique
			List<Staff> list = getCriteria().add(Restrictions.eq("login", login)).setMaxResults(1).list();
			if(list.size()<1){
				log.warn("User with login \"" + login + "\" has't been found");
				return null;
			}
			return list.get(0);
		}
		catch (HibernateException he){
			log.error("hibernate exception on finding staff by login" , he);
			throw new DAOException("Error of the stuff finding", he);
		}
	}


}
