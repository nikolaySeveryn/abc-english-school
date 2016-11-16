package nks.abc.dao.specification.chunks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;


public abstract class CompositeSpecification extends AbstractSpecification {
	
	private List<Specification> components;
	
	public abstract Junction getCompositeCriterion();
	
	public CompositeSpecification(Specification... specifications) {
		this.components = Arrays.asList(specifications);
	}
	
	public CompositeSpecification(List<Specification> specification) {
		this.components = specification;
	}
	
	public void addSpecification(Specification specification){
		components.add(specification);
	}
	
	
	@Override
	public Set<HibernateAlias> getAllAliases() {
		Set<HibernateAlias> aliases = new HashSet<HibernateAlias>();
		for(Specification specification : components){
			aliases.addAll(specification.getAllAliases());
		}
		return aliases;
	}

	@Override
	public Criterion toCriterion(Class<?> repositoryDomainClass){
		Junction criteria = getCompositeCriterion();
		for(Specification specification : components){
			criteria.add(specification.toCriterion(repositoryDomainClass));
		}
		return criteria;
	}
}
