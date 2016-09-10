package nks.abc.dao.newspecification.base;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;


public class DisjunctionSpecification extends CompositeSpecification {

	public DisjunctionSpecification(HibernateSpecification... specifications) {
		super(specifications);
	}
	
	public DisjunctionSpecification(List<HibernateSpecification> specification) {
		super(specification);
	}



	@Override
	public Junction getCompositeCriterion() {
		return Restrictions.disjunction();
	}

}
