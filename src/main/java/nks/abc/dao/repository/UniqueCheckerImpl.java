package nks.abc.dao.repository;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.base.interfaces.UniqueChecker;
import nks.abc.dao.exception.DAOException;

@Repository
@Transactional(readOnly=true)
public class UniqueCheckerImpl implements UniqueChecker{
	
	private static final Logger log = Logger.getLogger(UniqueCheckerImpl.class);
	private SessionFactory sessionFactory;
	
	@Autowired
	public void sessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean isUnique(Class<?> entity, String field, Object value) {
		if(entity == null || field == null || value == null){
			throw new NullPointerException("One of parameters is null");
		}
		field = field.trim();
		if(field.length() < 1){
			throw new IllegalArgumentException("Field name is empty");
		}
		try{
			Criteria criteria = getSession().createCriteria(entity);
			criteria.add(Restrictions.eq(field, value));
			criteria.setMaxResults(1);
			if(criteria.uniqueResult() == null){
				return true;
			}
			return false;
		}
		catch (Exception e){
			String message = "An error on the checing of the uniqueness of " + entity.getName() + "." + field;
			log.error(message);
			throw new DAOException(message, e);
		}
	}
	
	
}
