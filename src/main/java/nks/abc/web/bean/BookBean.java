package nks.abc.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nks.abc.bl.service.plan.BookService;
import nks.abc.bl.view.object.BookView;
import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.core.util.ExternalMessage;
import nks.abc.core.util.ExternalMessage.MessageSeverity;
import nks.abc.web.common.enumeration.EditingMode;

import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class BookBean implements Serializable {
	
	private static final long serialVersionUID = -1472298095198746868L;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private ExternalMessage message;
	
	private EditingMode mode = EditingMode.NONE;
	private BookView book = new BookView.NullBookDTO();
	
	public void add () {
		mode = EditingMode.ADD;
		book = new BookView(null, new String(), new String());
	}
	
	public void edit(BookView book) {
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
		this.book = new BookView.NullBookDTO();
		message.send(MessageSeverity.INFO, "Canceled");
	}
	
	public void delete(BookView book) {
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
	
	public List<BookView> getList() {
		try {
			return bookService.getAll();
		} catch (ServiceDisplayedErorr e) {
			message.send(MessageSeverity.ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
			return new ArrayList<BookView>();
		} catch (ServiceException e) {
			message.send(MessageSeverity.ERROR, "Error");
			e.printStackTrace();
			return new ArrayList<BookView>();
		}
	}
	
	public BookView getBook() {
		return book;
	}
	
	public void setBook(BookView book) {
		this.book = book;
	}

	public EditingMode getMode() {
		return mode;
	}

	public void setMode(EditingMode mode) {
		this.mode = mode;
	}
	
}
