package nks.abc.dao.specification.chunks.factory;

import nks.abc.dao.specification.chunks.EqualSpecification;
import nks.abc.dao.specification.chunks.HibernateAlias;

public class AliasChunksFactory implements SpecificationChunksFactory {
	
	private final String relativeField;
	private final Class<?> baseClass;
	
	/**
	 * 
	 * @param relativeField - Name of the filed in other entities that represent current entity  
	 * @param baseClass - Class of the current entity 
	 */
	public AliasChunksFactory(String relativeField, Class<?> baseClass) {
		super();
		this.relativeField = relativeField;
		this.baseClass = baseClass;
	}
	
	private HibernateAlias createAlias() {
		return new HibernateAlias(relativeField, baseClass);
	}
	
	@Override
	public EqualSpecification createEqualsSpecification() {
		EqualSpecification specification = new EqualSpecification();
		specification.setAlias(createAlias());
		return specification;
	}
	
	
}
