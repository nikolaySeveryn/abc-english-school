package nks.abc.domain.planing.factory;

import nks.abc.domain.planing.Book;
import nks.abc.domain.planing.impl.BookImpl;

public class BookFactory {
	public static Book createBook(){
		Book instance = new BookImpl();
		return instance;
	}
}
