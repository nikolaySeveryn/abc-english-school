package nks.abc.dao.base;

import java.util.List;

import nks.abc.dao.exception.DAOException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

public class BaseHibernrateRepositoryImpl <T> extends HibernateRepository implements BaseHibernateRepository<T>{
	
	private final static Logger log = Logger.getLogger(BaseHibernrateRepositoryImpl.class);
	
	public BaseHibernrateRepositoryImpl(Class<T> domainClass) {
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
	public List<T> query(HibernateSpecification specification) {
		try{
			return getCriteria().add(specification.toCriteria()).list();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on getting all data", he);
		}
	}
	
	@Override
	public T uniqueQuery(HibernateSpecification specification) {
		try{
			return (T) getCriteria().add(specification.toCriteria()).uniqueResult();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on getting all data", he);
		}
	}

	@Override
	public List<T> getAll() {
		return getCriteria().list();
	}
}
