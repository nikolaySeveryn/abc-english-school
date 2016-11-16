package nks.abc.dao.specification.factory;

import nks.abc.dao.specification.chunks.EqualSpecification;
import nks.abc.dao.specification.chunks.Specification;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;

public abstract class CommonSpecificationFactory {
	
	private final String idField;
	protected abstract SpecificationChunksFactory getChunksFactory();
	
	public CommonSpecificationFactory(String idField){
		this.idField = idField; 
	}
	
	public Specification byId(Long id){
		EqualSpecification specification = getChunksFactory().createEqualsSpecification();
		specification.setCriteria(idField, id);
		return specification;
	}
	
}