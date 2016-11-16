package nks.abc.dao.specification.chunks;

import java.util.HashSet;
import java.util.Set;


public abstract class SingleSpecification extends AbstractSpecification {

	private HibernateAlias alias;
	
	@Override
	public Set<HibernateAlias> getAllAliases() {
		Set<HibernateAlias>aliases = new HashSet<HibernateAlias>();
		if(this.alias != null) {
			aliases.add(alias);
		}
		return aliases;
	}
	
	public HibernateAlias getAlias() {
		return this.alias;
	}
	
	public void setAlias(HibernateAlias alias) {
		this.alias = alias;
	}
}
