package nks.abc.dao.specification.factory.user;

import nks.abc.dao.specification.chunks.factory.AliasChunksFactory;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;
import nks.abc.dao.specification.factory.CommonSpecificationFactory;
import nks.abc.domain.school.impl.GroupImpl;


public class GroupSpecificationFactory extends CommonSpecificationFactory {
	
	public static final GroupSpecificationFactory buildFactory(){
		return new GroupSpecificationFactory("group");
	}
	
	public static final GroupSpecificationFactory buildFactory(String relativeField){
		return new GroupSpecificationFactory(relativeField);
	}
	
	private final String relativeField;

	public GroupSpecificationFactory(String relativeField) {
		super("id");
		this.relativeField = relativeField;
	}

	@Override
	protected SpecificationChunksFactory getChunksFactory() {
		return new AliasChunksFactory(relativeField, GroupImpl.class);
	}

}
