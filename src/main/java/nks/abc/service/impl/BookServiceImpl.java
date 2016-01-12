package nks.abc.service.impl;

import java.util.List;

import nks.abc.dao.BookDAO;
import nks.abc.domain.dto.BookDTO;
import nks.abc.domain.dto.convertor.BookDTOConvertor;
import nks.abc.domain.entity.Book;
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
	public void save(BookDTO book) {
		bookDAO.insert(BookDTOConvertor.toEntity(book));
	}

	@Override
	@Transactional(readOnly=false)
	public void update(BookDTO book) {
		bookDAO.update(BookDTOConvertor.toEntity(book));
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(BookDTO book) {
		bookDAO.delete(BookDTOConvertor.toEntity(book));
	}

	@Override
	public BookDTO findById(Integer id) {
		return BookDTOConvertor.toDTO(bookDAO.findById(id));
	}

	@Override
	public List<BookDTO> getAll(Integer offset, Integer limit) {
		return BookDTOConvertor.toDTO(bookDAO.getAll(offset, limit));
	}

	@Override
	public List<BookDTO> getAll() {
		return BookDTOConvertor.toDTO(bookDAO.getAll());
	}
	
	
}
