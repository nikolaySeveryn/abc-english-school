package nks.abc.depricated.service.plan;

import java.util.List;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.dao.repository.planing.BookRepository;
import nks.abc.domain.planing.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("bookService")
@Transactional(readOnly=true)
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookDAO;
	
	private ErrorHandler errorHandler;
	
	@Autowired
	@Qualifier("serviceErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}

	@Override
	@Transactional(readOnly=false)
	public void add(Book book) {
		try{
			bookDAO.insert(book);
		}
		catch (Exception e){
			errorHandler.handle(e, "add book: " + book); 
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void update(Book book) {
		try{
			bookDAO.update(book);
		}
		catch (Exception e){
			errorHandler.handle(e, "update book: " + book);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Book book) {
		try{
			bookDAO.delete(book);
		}
		catch (Exception e){
			errorHandler.handle(e, "delete book:" + book);
		}
	}

	@Override
	public List<Book> getAll() {
		List<Book> books = null;
		try{
			books = bookDAO.retrieveAll();
		}
		catch (Exception e){
			errorHandler.handle(e, "get all books");
		}
		return books;
	}
}
