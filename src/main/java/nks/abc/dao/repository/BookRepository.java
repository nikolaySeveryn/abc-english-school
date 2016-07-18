package nks.abc.dao.repository;

import org.springframework.stereotype.Repository;

import nks.abc.bl.domain.Book;
import nks.abc.dao.base.BaseRepositoryImpl;

@Repository
public class BookRepository extends BaseRepositoryImpl<Book> {

	public BookRepository() {
		super(Book.class);
	}

}
