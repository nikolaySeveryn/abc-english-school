package nks.abc.dao.base;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nks.abc.dao.base.interfaces.BaseHibernateRepository;
import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.base.interfaces.HQLSpecification;
import nks.abc.dao.exception.DAOException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class BaseRepositoryImpl <T> extends HibernateRepository implements BaseHibernateRepository<T> {
	
	private static final int MAX_RESULT_OF_UNIQUE_QUERY = 1;
	private static final Logger log = Logger.getLogger(BaseRepositoryImpl.class);
	
	public BaseRepositoryImpl(Class<T> domainClass) {
		super(domainClass);
	}
	
	@Override
	public void insert(T entity) {
		try{
			log.info("insert entity: " + entity);
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
			log.info("update entity to: " + entity);
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
		log.info("delete entity: " + entity);
		getSession().delete(entity);
	}
	
	@Override
	public List<T> query(CriterionSpecification specification) {
		try{
			return getCriteria().add(specification.toCriteria()).list();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on getting all data", he);
		}
	}
	
	@Override
	public List<T> query(HQLSpecification specification){
		try {
			Query hqlQuery = getSession().createQuery(specification.toCriteria());
			setParameters(hqlQuery, specification.getParameters());
			return Collections.checkedList(hqlQuery.list(), domainClass);
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on getting all data", he);
		}
	}
	
	@Override
	public T uniqueQuery(HQLSpecification specification){
		try {
			Query hqlQuery = getSession().createQuery(specification.toCriteria());
			setParameters(hqlQuery, specification.getParameters());
			hqlQuery.setMaxResults(MAX_RESULT_OF_UNIQUE_QUERY);
			return (T) hqlQuery.uniqueResult();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new DAOException("Error on getting all data", he);
		}
	}
	
	@Override
	public T uniqueQuery(CriterionSpecification specification) {
		try{
			return (T) getCriteria().add(specification.toCriteria()).setMaxResults(MAX_RESULT_OF_UNIQUE_QUERY).uniqueResult();
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

	private void setParameters(Query hqlQuery, Map<String,Object> paramters) {
		for(Map.Entry<String, Object> param : paramters.entrySet()){
			if(param.getValue() instanceof Collection){
				hqlQuery.setParameterList(param.getKey(), (Collection) param.getValue());
			}
			else {
				hqlQuery.setParameter(param.getKey(), param.getValue());
			}
		}
	}
}