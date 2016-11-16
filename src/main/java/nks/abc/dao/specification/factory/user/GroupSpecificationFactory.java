package nks.abc.dao.specification.factory.user;

import nks.abc.dao.specification.chunks.factory.AliasChunksFactory;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;
import nks.abc.dao.specification.factory.CommonSpecificationFactory;
import nks.abc.domain.user.impl.GroupImpl;


public class GroupSpecificationFactory extends CommonSpecificationFactory {
	
	public static final GroupSpecificationFactory buildFactory(){
		return new GroupSpecificationFactory();
	}

	public GroupSpecificationFactory() {
		super("id");
	}

	@Override
	protected SpecificationChunksFactory getChunksFactory() {
		return new AliasChunksFactory("group", GroupImpl.class);
	}

}
