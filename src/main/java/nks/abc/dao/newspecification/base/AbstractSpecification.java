package nks.abc.dao.newspecification.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSpecification implements HibernateSpecification {
	
	@Override
	public HibernateSpecification or(HibernateSpecification... other) {
		List<HibernateSpecification> otherList = new ArrayList<HibernateSpecification>(Arrays.asList(other));
		otherList.add(this);
		return new DisjunctionSpecification(otherList);
	}

	@Override
	public HibernateSpecification and(HibernateSpecification... other) {
		List<HibernateSpecification> otherList = new ArrayList<HibernateSpecification>(Arrays.asList(other));
		otherList.add(this);
		return new ConjunctionSpecification(otherList);
	}

}
