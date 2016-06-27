package nks.abc.dao.base.interfaces;


public interface UniqueChecker {
	boolean isUnique(Class<?>entity, String field, Object value);
}
