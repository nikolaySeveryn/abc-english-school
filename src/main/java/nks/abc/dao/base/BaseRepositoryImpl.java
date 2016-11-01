package nks.abc.dao.base;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nks.abc.core.exception.repository.RepositoryException;
import nks.abc.dao.base.interfaces.BaseHibernateRepository;
import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.base.interfaces.HQLSpecification;
import nks.abc.dao.newspecification.base.HibernateAlias;
import nks.abc.dao.newspecification.base.HibernateSpecification;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Scope("prototype")
public class BaseRepositoryImpl <T> extends HibernateRepository implements BaseHibernateRepository<T> {
	
	private static final int MAX_RESULT_OF_UNIQUE_QUERY = 1;
	private static final Logger log = Logger.getLogger(BaseRepositoryImpl.class);
	
	public BaseRepositoryImpl(Class<? extends T> domainClass) {
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
			throw new RepositoryException("Error on insert", he);
		}
	}

	@Override
	public void update(T entity) {
		try{
			log.info("update entity [" + domainClass.getName() +"] to: \n" + entity);
			T merged = (T) getSession().merge(entity);
			getSession().update(merged);
		}
		catch (HibernateException he) {
			log.error("hibernate exception" , he);
			throw new RepositoryException("Error on update", he);
		}
	}
	
	@Override
	public void delete(T entity) {
		log.info("delete entity [" + domainClass.getName() +"]: \n" + entity);
		getSession().delete(entity);
	}
	
	public List<T> query(HibernateSpecification specification) {
		try{
			Criteria criteria = getCriteria();
			addAliasesToCriteria(specification, criteria);
			return criteria.add(specification.toCriterion()).list();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new RepositoryException("Error on getting all data", he);
		}
	}
	
	
	public T uniqueQuery(HibernateSpecification specification) {
		try{
			Criteria criteria = getCriteria();
			addAliasesToCriteria(specification, criteria);
			return (T) criteria.add(specification.toCriterion()).setMaxResults(MAX_RESULT_OF_UNIQUE_QUERY).uniqueResult();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new RepositoryException("Error on getting all data", he);
		}
	}

	private void addAliasesToCriteria(HibernateSpecification specification, Criteria criteria) {
		specification.checkBaseClass(domainClass);
		for(HibernateAlias alias: specification.getAliases()) {
			criteria.createAlias(alias.getAssosiationPath(), alias.getName());
		}
	}
	
	@Override
	public List<T> query(CriterionSpecification specification) {
		try{
			return getCriteria().add(specification.toCriteria()).list();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new RepositoryException("Error on getting all data", he);
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
			throw new RepositoryException("Error on getting all data", he);
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
			throw new RepositoryException("Error on getting all data", he);
		}
	}
	
	@Override
	public T uniqueQuery(CriterionSpecification specification) {
		try{
			return (T) getCriteria().add(specification.toCriteria()).setMaxResults(MAX_RESULT_OF_UNIQUE_QUERY).uniqueResult();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new RepositoryException("Error on getting all data", he);
		}
	}
	

	@Override
	public List<T> retrieveAll() {
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