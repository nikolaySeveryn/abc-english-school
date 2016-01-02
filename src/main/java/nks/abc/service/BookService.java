package nks.abc.service;

import java.util.List;

import nks.abc.domain.dto.BookDTO;

public interface BookService {
	
	public void save(BookDTO book);
	public void update(BookDTO book);
	public void delete(BookDTO book);
	public BookDTO findById(Integer id);
	public List<BookDTO> getAll(Integer offset, Integer limit);
	public List<BookDTO> getAll();
}