package nks.abc.dao.newspecification.base;

import java.util.Set;

import org.hibernate.criterion.Criterion;

public interface HibernateSpecification {
	Criterion toCriterion();
	Set<HibernateAlias> getAliases();
	void checkBaseClass(Class<?>domainClass);
	HibernateSpecification or(HibernateSpecification... other);
	HibernateSpecification and(HibernateSpecification... other);
}
