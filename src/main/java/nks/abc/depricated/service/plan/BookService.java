package nks.abc.depricated.service.plan;

import java.util.List;

import nks.abc.domain.planing.Book;

public interface BookService {
	
	public void add(Book book);
	public void update(Book book);
	public void delete(Book book);
	public List<Book> getAll();
}