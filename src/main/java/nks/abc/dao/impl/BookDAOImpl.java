package nks.abc.dao.impl;

import nks.abc.dao.BookDAO;
import nks.abc.domain.entity.Book;

import org.springframework.stereotype.Service;


@Service("bookDAO")
public class BookDAOImpl extends BaseDAOImpl<Book, Integer> implements BookDAO {

	public BookDAOImpl() {
		super(Book.class);
	}
	
}
