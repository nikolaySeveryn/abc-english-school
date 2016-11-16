package nks.abc.dao.repository.planing;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.planing.Book;
import nks.abc.domain.planing.impl.BookImpl;

@Repository
public class BookRepository extends BaseRepositoryImpl<Book> {

	public BookRepository() {
		super(BookImpl.class);
	}

}
