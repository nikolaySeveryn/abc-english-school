package nks.abc.bl.service.plan;

import java.util.List;

import nks.abc.bl.domain.Book;
import nks.abc.bl.view.converter.BookViewConverter;
import nks.abc.bl.view.object.BookView;
import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.repository.RepositoryException;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.dao.repository.BookRepository;

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
	public void add(BookView bookView) {
		Book bookEntity = BookViewConverter.toEntity(bookView);
		try{
			bookDAO.insert(bookEntity);
		}
		catch (Exception e){
			errorHandler.handle(e, "add book: " + bookView); 
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void update(BookView bookView) {
		Book bookEntity = BookViewConverter.toEntity(bookView);
		try{
			bookDAO.update(bookEntity);
		}
		catch (Exception e){
			errorHandler.handle(e, "update book: " + bookView);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(BookView book) {
		Book bookEntity = BookViewConverter.toEntity(book);
		try{
			bookDAO.delete(bookEntity);
		}
		catch (Exception e){
			errorHandler.handle(e, "delete book:" + book);
		}
	}

	@Override
	public List<BookView> getAll() {
		List<Book> all = null;
		try{
			all = bookDAO.getAll();
		}
		catch (Exception e){
			errorHandler.handle(e, "get all books");
		}
		return BookViewConverter.toView(all);
	}
}
