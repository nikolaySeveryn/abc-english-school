package nks.abc.dao.base;

import java.util.List;

import nks.abc.core.exception.repository.RepositoryException;
import nks.abc.dao.specification.chunks.HibernateAlias;
import nks.abc.dao.specification.chunks.Specification;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class BaseRepositoryImpl <T> extends HibernateRepository implements BaseRepository<T> {
	
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
	
	@Override
	public List<T> retrieveAll() {
		return createCriteria().list();
	}
	
	
	
	@Override
	public List<T> query(Specification specification) {
		try{
			Criteria criteria = createCriteria();
			addAliasesToCriteria(specification, criteria);
			return criteria.add(specification.toCriterion(domainClass)).list();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new RepositoryException("Error on getting all data", he);
		}
	}
	
	@Override
	public T uniqueQuery(Specification specification) {
		try{
			Criteria criteria = createCriteria();
			addAliasesToCriteria(specification, criteria);
			return (T) criteria.add(specification.toCriterion(domainClass))
					.setMaxResults(MAX_RESULT_OF_UNIQUE_QUERY).uniqueResult();
		}
		catch (HibernateException he){
			log.error("hibernate exception" , he);
			throw new RepositoryException("Error on executing unique query", he);
		}
	}

	private void addAliasesToCriteria(Specification specification, Criteria criteria) {
		for(HibernateAlias alias: specification.getAllAliases()) {
			if(alias.isNeeded(domainClass)) {
				criteria.createAlias(alias.getAssosiationPath(), alias.getAliasName());
			}
		}
	}
}