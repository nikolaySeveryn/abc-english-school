package nks.abc.domain.planing.impl;

import java.util.List;

import nks.abc.dao.base.RepositoryException;
import nks.abc.dao.repository.planing.BookRepository;
import nks.abc.domain.exception.CrudException;
import nks.abc.domain.planing.Book;
import nks.abc.domain.planing.Library;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly=true)
public class LibraryImpl implements Library {
	
	private final static Logger log = Logger.getLogger(LibraryImpl.class);
	
	@Autowired
	private BookRepository bookDAO;
	
	@Override
	@Transactional(readOnly=false)
	public void add(Book book) {
		try{
			bookDAO.insert(book);
		}
		catch (RepositoryException e){
			log.error("Error on inserting book", e);
			throw new CrudException("Error on book inserting", e);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void update(Book book) {
		try{
			bookDAO.update(book);
		}
		catch (RepositoryException e){
			log.error("Error on update book",e);
			throw new CrudException("Error on book updationg", e);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Book book) {
		try{
			bookDAO.delete(book);
		}
		catch (RepositoryException e){
			log.error("Error on deleting book: " + book, e);
			throw new CrudException("Error on book deleting", e);
		}
	}

	@Override
	public List<Book> getAll() {
		try{
			return bookDAO.retrieveAll();
		}
		catch (RepositoryException e){
			log.error("error on getting all books", e);
			throw new CrudException("error on getting all books", e);
		}
	}
}
