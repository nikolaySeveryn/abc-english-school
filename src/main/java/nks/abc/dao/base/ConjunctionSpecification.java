package nks.abc.dao.base;

import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class ConjunctionSpecification implements HibernateSpecification {

	private List<HibernateSpecification> specifications;
	
	public ConjunctionSpecification(HibernateSpecification... specifications) {
		this.specifications = Arrays.asList(specifications);
	}
	
	public void addSpecification(HibernateSpecification specification){
		specifications.add(specification);
	}

	@Override
	public Criterion toCriteria() {
		Conjunction criteria = Restrictions.conjunction();
		for(HibernateSpecification specification : specifications){
			criteria.add(specification.toCriteria());
		}
		return criteria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((specifications == null) ? 0 : specifications.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConjunctionSpecification other = (ConjunctionSpecification) obj;
		if (specifications == null) {
			if (other.specifications != null)
				return false;
		} else if (!specifications.equals(other.specifications))
			return false;
		return true;
	}
}
