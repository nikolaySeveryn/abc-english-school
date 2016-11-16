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
	
	
	
}
