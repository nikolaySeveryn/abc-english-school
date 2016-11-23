package nks.abc.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nks.abc.domain.exception.DomainException;
import nks.abc.domain.planing.Book;
import nks.abc.domain.planing.Library;
import nks.abc.domain.planing.factory.BookFactory;
import nks.abc.web.common.enumeration.BeanState;
import nks.abc.web.common.message.MessageSeverity;
import nks.abc.web.common.message.UIMessage;

import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class BookBean implements Serializable {
	
	private static final long serialVersionUID = 2690374037612901707L;

	private static final Logger log = Logger.getLogger(BookBean.class);

	@Autowired
	private Library bookService;
	
	@Autowired
	private UIMessage message;
	
	private BeanState state = BeanState.LIST;
	private Book book;
	
	public void add () {
		state = BeanState.ADD;
		book = BookFactory.createBook();
	}
	
	public void edit(Book book) {
		//TODO: load book via id
		state = BeanState.EDIT;
		this.book = book;
	}
	
	public void save() {
		try {
			if(state.equals(BeanState.ADD)){
				System.out.println("save adding");
				bookService.add(book);
				message.send(MessageSeverity.INFO, "Added");
			}
			else if(state.equals(BeanState.EDIT)){
				System.out.println("save editing");
				bookService.update(book);
				message.send(MessageSeverity.INFO, "Updated");
			}
			else {
				log.error("undefined bean state");
				message.sendError();
			}
		} catch (Exception e) {
			log.error("error on book saving", e);
			message.sendError();
		}
	}

	public void cancel(){
		this.state = BeanState.LIST;
		message.send(MessageSeverity.INFO, "Canceled");
	}
	
	public void delete(Book book) {
		try {
			bookService.delete(book);
			message.send(MessageSeverity.WARNING, "Deleted");
		} catch (Exception e) {
			log.error("erro on book deleting", e);
			message.sendError();
		}	
	}
	
	public List<Book> getList() {
		List<Book>books = new ArrayList<Book>();
		try {
			books = bookService.getAll();
		} catch (Exception e) {
			log.error("erro on getting all books");
			message.sendError();
		}
		return books;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}

	public BeanState getMode() {
		return state;
	}

	public void setMode(BeanState mode) {
		this.state = mode;
	}
	
}