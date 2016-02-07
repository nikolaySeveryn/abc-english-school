package nks.abc.dao.base;

import java.util.List;

public interface BaseHibernateRepository<T> {

	void insert(T entity);
	void update(T entity);
	void delete(T entity);
	List<T> query(HibernateSpecification specification);
	T uniqueQuery(HibernateSpecification specification);
	List<T> getAll();
}