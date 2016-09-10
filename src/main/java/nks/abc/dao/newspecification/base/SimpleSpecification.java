package nks.abc.dao.newspecification.base;

import java.util.HashSet;
import java.util.Set;


public abstract class SimpleSpecification extends AbstractSpecification {

	private HibernateAlias alias;
	
	@Override
	public Set<HibernateAlias> getAliases() {
		Set<HibernateAlias>aliases = new HashSet<HibernateAlias>();
		if(this.alias != null){
			this.alias.addToCollection(aliases);
		}
		return aliases;
	}
	
	@Override
	public void checkBaseClass(Class<?> domainClass) {
		if(alias != null) {
			alias.checkBaseClass(domainClass);
		}
	}

	public HibernateAlias getAlias() {
		return this.alias;
	}
	
	public void setAlias(HibernateAlias alias) {
		this.alias = alias;
	}
}
