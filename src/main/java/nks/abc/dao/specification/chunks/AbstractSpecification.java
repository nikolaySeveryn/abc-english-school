package nks.abc.dao.specification.chunks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSpecification implements Specification {
	
	@Override
	public Specification or(Specification... other) {
		List<Specification> otherList = new ArrayList<Specification>(Arrays.asList(other));
		otherList.add(this);
		return new DisjunctionSpecification(otherList);
	}

	@Override
	public Specification and(Specification... other) {
		List<Specification> otherList = new ArrayList<Specification>(Arrays.asList(other));
		otherList.add(this);
		return new ConjunctionSpecification(otherList);
	}

}
