package nks.abc.dao.specification.chunks;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;


public class DisjunctionSpecification extends CompositeSpecification {

	public DisjunctionSpecification(Specification... specifications) {
		super(specifications);
	}
	
	public DisjunctionSpecification(List<Specification> specification) {
		super(specification);
	}



	@Override
	public Junction getCompositeCriterion() {
		return Restrictions.disjunction();
	}

}
