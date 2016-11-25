package nks.abc.dao.specification.chunks;


public class HibernateAlias {
	
	private String assosiationPath;
	private Class<?>baseClass;
	
	/**
	 * 
	 * @param assosiationPath - name of the field that contains associated entity in other related entities
	 * @param baseClass - type of the entity associated with alias
	 */
	public HibernateAlias(String assosiationPath, Class<?> baseClass) {
		super();
		this.assosiationPath = assosiationPath;
		this.baseClass = baseClass;
	}
	
	public String getAssosiationPath() {
		return assosiationPath;
	}
	
	public String getAliasName() {
		return baseClass.getSimpleName();
	}
	
	/**
	 * This method check should client use this alias or not
	 * 
	 * @param - Class associated with repository
	 * @return
	 */
	public Boolean isNeeded(Class<?> repositoryBaseClass){
		if(baseClass.isAssignableFrom(repositoryBaseClass)){
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assosiationPath == null) ? 0 : assosiationPath.hashCode());
		result = prime * result + ((baseClass == null) ? 0 : baseClass.hashCode());
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
		if (assosiationPath == null) {
			if (other.assosiationPath != null)
				return false;
		}
		else if (!assosiationPath.equals(other.assosiationPath))
			return false;
		if (baseClass == null) {
			if (other.baseClass != null)
				return false;
		}
		else if (!baseClass.equals(other.baseClass))
			return false;
		return true;
	}
	
	
	
}
