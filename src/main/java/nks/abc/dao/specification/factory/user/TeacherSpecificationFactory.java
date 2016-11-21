package nks.abc.dao.specification.factory.user;

import nks.abc.dao.specification.chunks.factory.AliasChunksFactory;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;
import nks.abc.dao.specification.factory.CommonSpecificationFactory;
import nks.abc.domain.user.impl.TeacherImpl;


public class TeacherSpecificationFactory extends CommonSpecificationFactory {

	public static TeacherSpecificationFactory buildFactory(){
		return new TeacherSpecificationFactory();
	}
	
	public TeacherSpecificationFactory() {
		super("userId");
	}

	@Override
	protected SpecificationChunksFactory getChunksFactory() {
		return new AliasChunksFactory("teacher", TeacherImpl.class);
	}

}
