package nks.abc.service.impl;

import java.util.List;

import nks.abc.dao.BookDAO;
import nks.abc.domain.Book;
import nks.abc.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("bookService")
@Transactional(readOnly=true)
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDAO bookDAO;

	@Override
	@Transactional(readOnly=false)
	public void save(Book book) {
		bookDAO.save(book);
	}

	@Override
	public void update(Book book) {
		bookDAO.update(book);
	}

	@Override
	public void delete(Book book) {
		bookDAO.delete(book);
	}

	@Override
	public Book findById(Integer id) {
		return bookDAO.findById(id);
	}

	@Override
	public List<Book> getAll(Integer offset, Integer limit) {
		return bookDAO.getAll(offset, limit);
	}

	@Override
	public List<Book> getAll() {
		return bookDAO.getAll();
	}
	
	
}
