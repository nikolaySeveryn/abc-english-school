package nks.abc.dao.specification.factory.planing;

import nks.abc.dao.specification.chunks.factory.AliasChunksFactory;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;
import nks.abc.domain.planing.impl.BookImpl;

public class BookSpecificationFactory{
	
	private SpecificationChunksFactory chunksFactory = new AliasChunksFactory("book", BookImpl.class);
	
	public static BookSpecificationFactory buildBookSpecificationFactory(){
		return new BookSpecificationFactory();
	}
	
	
	
}
