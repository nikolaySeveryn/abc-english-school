package nks.abc.service.impl;

import java.util.List;

import nks.abc.dao.exception.DAOException;
import nks.abc.dao.repository.BookDAO;
import nks.abc.domain.dto.BookDTO;
import nks.abc.domain.dto.converter.BookDTOConverter;
import nks.abc.domain.entity.Book;
import nks.abc.service.BookService;
import nks.abc.service.exception.ServiceException;

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
	public void add(BookDTO book) {
		Book bookEntity = BookDTOConverter.toEntity(book);
		try{
			bookDAO.insert(bookEntity);
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void update(BookDTO book) {
		Book bookEntity = BookDTOConverter.toEntity(book);
		try{
			bookDAO.update(bookEntity);
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(BookDTO book) {
		Book bookEntity = BookDTOConverter.toEntity(book);
		try{
			bookDAO.delete(bookEntity);
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
	}

//	@Override
//	public BookDTO findById(Integer id) {
//		Book bookEntity = null;
//		try{
//			bookEntity = bookDAO.findById(id);
//		}
//		catch (DAOException de){
//			throw new ServiceException("dao error", de);
//		}
//		return BookDTOConvertor.toDTO(bookEntity);
//	}

//	@Override
//	public List<BookDTO> getAll(Integer offset, Integer limit) {
//		List<Book> allBooks = null;
//		try {
//			allBooks = bookDAO.getAll(offset, limit);
//		}
//		catch (DAOException de){
//			throw new ServiceException("dao error", de);
//		}
//		return BookDTOConvertor.toDTO(allBooks);
//	}

	@Override
	public List<BookDTO> getAll() {
		List<Book> all = null;
		try{
			all = bookDAO.getAll();
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
		return BookDTOConverter.toDTO(all);
	}
	
	
}
