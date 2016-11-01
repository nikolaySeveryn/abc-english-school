package nks.abc.dao.repository;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.core.exception.repository.RepositoryException;
import nks.abc.dao.base.interfaces.UniqueChecker;

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
	public boolean isUnique(Class<?> entity, String field, Object value, String idField, Object verifiableEntiryId) {
		if(entity == null || field == null || value == null){
			throw new IllegalArgumentException("Checking of uniqueness is failed. Entity tyme, field name or value is null");
		}
		field = field.trim();
		if(field.length() < 1){
			throw new IllegalArgumentException("Checking of uniqueness is failed. Field name is empty");
		}
		try{
			Criteria criteria = getSession().createCriteria(entity);
			criteria.add(Restrictions.eq(field, value));
			if(idField != null && verifiableEntiryId != null){
				criteria.add(Restrictions.ne(idField, verifiableEntiryId));
			}
			criteria.setMaxResults(1);
			if(criteria.uniqueResult() == null){
				return true;
			}
			return false;
		}
		catch (Exception e){
			String message = "An error on the checing of the uniqueness of " + entity.getName() + "." + field;
			log.error(message);
			throw new RepositoryException(message, e);
		}
	}
	
	
}
