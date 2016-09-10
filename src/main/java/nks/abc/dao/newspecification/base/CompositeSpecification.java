package nks.abc.dao.newspecification.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;


public abstract class CompositeSpecification extends AbstractSpecification {
	
	private List<HibernateSpecification> components;
	
	public abstract Junction getCompositeCriterion();
	
	public CompositeSpecification(HibernateSpecification... specifications) {
		this.components = Arrays.asList(specifications);
	}
	
	public CompositeSpecification(List<HibernateSpecification> specification) {
		this.components = specification;
	}
	
	public void addSpecification(HibernateSpecification specification){
		components.add(specification);
	}
	
	
	@Override
	public void checkBaseClass(Class<?> domainClass) {
		for(HibernateSpecification component : this.components){
			component.checkBaseClass(domainClass);
		}
	}

	@Override
	public Set<HibernateAlias> getAliases() {
		Set<HibernateAlias> aliases = new HashSet<HibernateAlias>();
		for(HibernateSpecification specification : components){
			for(HibernateAlias aliase : specification.getAliases()){
				aliase.addToCollection(aliases);
			}
//			aliases.addAll(specification.getAliases());
		}
		return aliases;
	}

	@Override
	public Criterion toCriterion(){
		Junction criteria = getCompositeCriterion();
		for(HibernateSpecification specification : components){
			criteria.add(specification.toCriterion());
		}
		return criteria;
	}
}
