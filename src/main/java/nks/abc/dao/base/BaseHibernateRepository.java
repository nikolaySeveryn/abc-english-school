package nks.abc.dao.base;

import java.util.List;

public interface BaseHibernateRepository<T> {

	void insert(T entity);
	void update(T entity);
	void delete(T entity);
	List<T> query(CriterionSpecification specification);
	List<T> query(HQLSpecification specification);
	T uniqueQuery(CriterionSpecification specification);
	T uniqueQuery(HQLSpecification specification);
	List<T> getAll();
}