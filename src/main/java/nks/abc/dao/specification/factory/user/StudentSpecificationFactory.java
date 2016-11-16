package nks.abc.dao.specification.factory.user;

import nks.abc.dao.specification.chunks.factory.AliasChunksFactory;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;
import nks.abc.dao.specification.factory.CommonSpecificationFactory;
import nks.abc.domain.user.impl.StudentImpl;

public class StudentSpecificationFactory extends CommonSpecificationFactory{
	
	public static StudentSpecificationFactory buildFactory() {
		return new StudentSpecificationFactory();
	}
	
	public StudentSpecificationFactory() {
		super("id");
	}

	@Override
	protected SpecificationChunksFactory getChunksFactory() {
		return new AliasChunksFactory("student", StudentImpl.class);
	}

}
