package nks.abc.depricated.service.plan;

import java.util.List;

import nks.abc.depricated.view.object.BookView;

public interface BookService {
	
	public void add(BookView book);
	public void update(BookView book);
	public void delete(BookView book);
//	public BookDTO findById(Integer id);
//	public List<BookDTO> getAll(Integer offset, Integer limit);
	public List<BookView> getAll();
}