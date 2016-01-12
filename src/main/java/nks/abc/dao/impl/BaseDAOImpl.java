package nks.abc.dao.impl;

import java.io.Serializable;
import java.util.List;

import nks.abc.dao.BaseDAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAOImpl <T,P extends Serializable> implements BaseDAO<T, P>{
	
	private final Class<T> domainClass;
	private SessionFactory sessionFactory;
	
	public BaseDAOImpl(Class<T> domainClass) {
		this.domainClass = domainClass;
	}
	
	@Autowired
	public void sessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected Criteria getCriteria() {
		return getSession().createCriteria(domainClass);
	}
	
	@Override
	public void insert(T entity) {
		getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}
	
	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	@Override
	public void deleteById(P id) {
		delete(findById(id));
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findById(P id) {
		return (T) getSession().get(domainClass, id);
	}

	@Override
	public List<T> getAll() {
		return getAll(0, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Integer offset, Integer limit) {
		Criteria crit = getCriteria().setFirstResult(offset);
		if(limit != null){
			crit.setMaxResults(limit);
		}
		return crit.list();
	}
}
