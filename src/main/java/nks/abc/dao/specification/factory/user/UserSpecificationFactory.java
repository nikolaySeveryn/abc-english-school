package nks.abc.dao.specification.factory.user;

import nks.abc.dao.specification.chunks.factory.AliasChunksFactory;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;
import nks.abc.dao.specification.factory.CommonSpecificationFactory;
import nks.abc.domain.user.impl.UserImpl;

public class UserSpecificationFactory extends CommonSpecificationFactory{
	
	public static UserSpecificationFactory buildFactory(){
		return new UserSpecificationFactory();
	}
	
	@Override
	protected SpecificationChunksFactory getChunksFactory() {
		return new AliasChunksFactory("user", UserImpl.class);
	}
	
	public UserSpecificationFactory() {
		super("id");
	}
}