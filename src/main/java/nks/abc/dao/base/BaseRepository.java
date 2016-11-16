package nks.abc.dao.base;

import java.util.List;

import nks.abc.dao.specification.chunks.Specification;

public interface BaseRepository<T> {

	void insert(T entity);
	void update(T entity);
	void delete(T entity);
	List<T> query(Specification specification);
	T uniqueQuery(Specification specification);
	List<T> retrieveAll();
}