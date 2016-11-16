package nks.abc.domain.planing;

import java.util.List;

public interface Library {
	
	public void add(Book book);
	public void update(Book book);
	public void delete(Book book);
	public List<Book> getAll();
}