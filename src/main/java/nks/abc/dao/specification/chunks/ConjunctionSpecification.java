package nks.abc.dao.specification.chunks;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;


public class ConjunctionSpecification extends CompositeSpecification {

	public ConjunctionSpecification(Specification... specifications) {
		super(specifications);
	}

	public ConjunctionSpecification(List<Specification> specification) {
		super(specification);
	}

	@Override
	public Junction getCompositeCriterion() {
		return Restrictions.conjunction();
	}

}
