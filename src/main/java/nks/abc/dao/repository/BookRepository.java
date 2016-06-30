package nks.abc.dao.repository;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.entity.Book;

@Repository
public class BookRepository extends BaseRepositoryImpl<Book> {

	public BookRepository() {
		super(Book.class);
	}

}
