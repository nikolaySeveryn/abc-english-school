package nks.abc.dao.repository;

import nks.abc.dao.base.BaseHibernrateRepositoryImpl;
import nks.abc.domain.entity.Book;

import org.springframework.stereotype.Service;


@Service("bookDAO")
public class BookDAO extends BaseHibernrateRepositoryImpl<Book> {

	public BookDAO() {
		super(Book.class);
	}
	
}
