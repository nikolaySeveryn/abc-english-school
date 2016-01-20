package nks.abc.dao.impl;

import java.io.Serializable;
import java.util.List;

import nks.abc.dao.BaseDAO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class BaseDAOImpl <T,P extends Serializable> extends DAO implements BaseDAO<T, P>{
	
	public BaseDAOImpl(Class<T> domainClass) {
		super(domainClass);
	}
	
	@Override
	public void insert(T entity) {
		getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		T merged = (T) getSession().merge(entity);
		getSession().update(merged);
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
