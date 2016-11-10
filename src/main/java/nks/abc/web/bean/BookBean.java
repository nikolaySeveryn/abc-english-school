package nks.abc.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.depricated.service.plan.BookService;
import nks.abc.domain.planing.Book;
import nks.abc.domain.planing.factory.BookFactory;
import nks.abc.web.common.enumeration.EditingMode;
import nks.abc.web.common.message.MessageSeverity;
import nks.abc.web.common.message.UIMessage;

import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class BookBean implements Serializable {
	
	private static final long serialVersionUID = -1472298095198746868L;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UIMessage message;
	
	private EditingMode mode = EditingMode.NONE;
	private Book book;
	
	public void add () {
		mode = EditingMode.ADD;
		book = BookFactory.createBook();
	}
	
	public void edit(Book book) {
		//TODO: load book via id
		mode = EditingMode.EDIT;
		this.book = book;
	}
	
	public void save() {
		try {
			if(mode.equals(EditingMode.ADD)){
				System.out.println("save adding");
				bookService.add(book);
				message.send(MessageSeverity.INFO, "Added");
			}
			else if(mode.equals(EditingMode.EDIT)){
				System.out.println("save editing");
				bookService.update(book);
				message.send(MessageSeverity.INFO, "Updated");
			}
			else {
				message.send(MessageSeverity.ERROR, "Error");
			}
		} catch (ServiceDisplayedErorr e) {
			message.send(MessageSeverity.ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
		} catch (ServiceException e) {
			message.send(MessageSeverity.ERROR, "Error");
			e.printStackTrace();
		}
	}

	public void cancel(){
		this.mode = EditingMode.NONE;
		message.send(MessageSeverity.INFO, "Canceled");
	}
	
	public void delete(Book book) {
		try {
			bookService.delete(book);
			message.send(MessageSeverity.WARNING, "Deleted");
		} catch (ServiceDisplayedErorr e) {
			message.send(MessageSeverity.ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
		} catch (ServiceException e) {
			message.send(MessageSeverity.ERROR, "Error");
			e.printStackTrace();
		}	
	}
	
	public List<Book> getList() {
		try {
			return bookService.getAll();
		} catch (ServiceDisplayedErorr e) {
			message.send(MessageSeverity.ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
			return new ArrayList<Book>();
		} catch (ServiceException e) {
			message.send(MessageSeverity.ERROR, "Error");
			e.printStackTrace();
			return new ArrayList<Book>();
		}
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}

	public EditingMode getMode() {
		return mode;
	}

	public void setMode(EditingMode mode) {
		this.mode = mode;
	}
	
}