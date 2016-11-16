package nks.abc.dao.base;


public interface UniqueChecker {
	boolean isUnique(Class<?>entity, String field, Object value, String idField, Object verifiableEntiryId);
}
