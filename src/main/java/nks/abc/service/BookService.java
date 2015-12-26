package nks.abc.service;

import java.util.List;

import nks.abc.domain.Book;

public interface BookService {
	
	public void save(Book book);
	public void update(Book book);
	public void delete(Book book);
	public Book findById(Integer id);
	public List<Book> getAll(Integer offset, Integer limit);
	public List<Book> getAll();
}