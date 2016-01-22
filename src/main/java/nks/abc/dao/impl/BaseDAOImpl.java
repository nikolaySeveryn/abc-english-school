package nks.abc.dao.impl;

import java.io.Serializable;
import java.util.List;

import nks.abc.dao.BaseDAO;
import nks.abc.dao.exception.DAOException;
import nks.abc.dao.impl.User.StaffDAOImpl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;

public class BaseDAOImpl <T,P extends Serializable> extends DAO implements BaseDAO<T, P>{
	
	private final static Logger log = Logger.getLogger(BaseDAOImpl.class);
	
	public BaseDAOImpl(Class<T> domainClass) {
		super(domainClass);
	}
	
	@Override
	public void insert(T entity) {
		try{
			getSession().save(entity);
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on insert", he);
		}
	}

	@Override
	public void update(T entity) {
		try{
			T merged = (T) getSession().merge(entity);
			getSession().update(merged);
		}
		catch (HibernateException he) {
			log.error("hibernate exception" , he);
			throw new DAOException("Error on update", he);
		}
	}
	
	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	@Override
	public void deleteById(P id) {
		try{
			delete(findById(id));
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on delete", he);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findById(P id) {
		try{
			return (T) getSession().get(domainClass, id);
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on serching data by id", he);
		}
	}

	@Override
	public List<T> getAll() {
		return getAll(0, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Integer offset, Integer limit) {
		try{
			Criteria crit = getCriteria().setFirstResult(offset);
			if(limit != null){
				crit.setMaxResults(limit);
			}
			return crit.list();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on getting all data", he);
		}
	}
}
