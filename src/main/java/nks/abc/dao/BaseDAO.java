package nks.abc.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T, P extends Serializable> {

	public void save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public void deleteById(P id);

	public T findById(P id);
	
	public List<T> getAll();
	
	public List<T> getAll(Integer offset, Integer limit);

}