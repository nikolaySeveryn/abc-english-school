package nks.abc.dao.specification.chunks;

import java.util.Set;

import org.hibernate.criterion.Criterion;

public interface Specification {
	/**
	 * This method creates and returns criterion for specification
	 * 
	 * @param repositoryDomainClass - Class of associated with repository doamin
	 * @return
	 */
	Criterion toCriterion(Class<?> repositoryDomainClass);
	Set<HibernateAlias> getAllAliases();
	Specification or(Specification... other);
	Specification and(Specification... other);
}
