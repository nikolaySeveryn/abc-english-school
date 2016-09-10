package nks.abc.dao.newspecification.base;

import java.util.Collection;

public class HibernateAlias {
	private String assosiationPath;
	private String name;
	private Class<?>baseClass;
	private Boolean isAliasNeeded;
	
	
	public HibernateAlias(String assosiationPath, String alias, Class<?> baseClass) {
		super();
		this.assosiationPath = assosiationPath;
		this.name = alias;
		this.baseClass = baseClass;
	}
	
	public String getAssosiationPath() {
		return assosiationPath;
	}
	
	public String getName() {
		return name;
	}
	
	public void checkBaseClass(Class<?> domainClass) {
		// we don't need alias if it base class is the same as repository's domain class
		// otherwise we will get org.hibernate.QueryException: could not resolve property
		isAliasNeeded = ! this.baseClass.equals(domainClass);
	}
	
	public Boolean isNeeded(){
		return isAliasNeeded;
	}
	
	public void addToCollection(Collection<HibernateAlias>aliases) {
		if(isAliasNeeded){
			aliases.add(this);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		HibernateAlias other = (HibernateAlias) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}
}
