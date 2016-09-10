package nks.abc.dao.newspecification.base;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;


public class ConjunctionSpecification extends CompositeSpecification {

	public ConjunctionSpecification(HibernateSpecification... specifications) {
		super(specifications);
	}

	public ConjunctionSpecification(List<HibernateSpecification> specification) {
		super(specification);
	}

	@Override
	public Junction getCompositeCriterion() {
		return Restrictions.conjunction();
	}

}
