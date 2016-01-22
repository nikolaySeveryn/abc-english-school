package nks.abc.web;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nks.abc.domain.dto.BookDTO;
import nks.abc.service.BookService;
import nks.abc.service.exception.ServiceDisplayedErorr;
import nks.abc.service.exception.ServiceException;

import java.io.Serializable;

@Component
@ManagedBean(name="bookController")
@SessionScoped
public class BookController implements Serializable {
	
	private static final long serialVersionUID = -1472298095198746868L;

	public enum EditMode{
		NONE,
		EDIT,
		ADD
	}
	
	@Autowired
	private BookService bookService;
	
	private EditMode mode = EditMode.NONE;
	private BookDTO book = new BookDTO.NullBookDTO();
	
	public void add () {
		mode = EditMode.ADD;
		book = new BookDTO(null, new String(), new String());
	}
	
	public void edit(BookDTO book) {
		mode = EditMode.EDIT;
		this.book = book;
	}
	
	public void save() {
		try {
			if(mode.equals(EditMode.ADD)){
				System.out.println("save adding");
				bookService.save(book);
				addMessage(FacesMessage.SEVERITY_INFO, "Added");
			}
			else if(mode.equals(EditMode.EDIT)){
				System.out.println("save editing");
				bookService.update(book);
				addMessage(FacesMessage.SEVERITY_INFO, "Updated");
			}
			else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			}
		} catch (ServiceDisplayedErorr e) {
			addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
		} catch (ServiceException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			e.printStackTrace();
		}
	}

	public void cancel(){
		this.mode = EditMode.NONE;
		this.book = new BookDTO.NullBookDTO();
		addMessage(FacesMessage.SEVERITY_INFO, "скасовано");
	}
	
	public void delete(BookDTO book) {
		try {
			bookService.delete(book);
			addMessage(FacesMessage.SEVERITY_WARN, "Deleted");
		} catch (ServiceDisplayedErorr e) {
			addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
		} catch (ServiceException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			e.printStackTrace();
		}	
	}
	
	public List<BookDTO> getList() {
		try {
			return bookService.getAll();
		} catch (ServiceDisplayedErorr e) {
			addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
			return new ArrayList<BookDTO>();
		} catch (ServiceException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			e.printStackTrace();
			return new ArrayList<BookDTO>();
		}
	}
	
	
	
	public BookDTO getBook() {
		return book;
	}
	
	public void setBook(BookDTO book) {
		this.book = book;
	}

	public EditMode getMode() {
		return mode;
	}

	public void setMode(EditMode mode) {
		this.mode = mode;
	}
	
	private void addMessage(Severity severity, String msg) {
		FacesMessage message = new FacesMessage(severity, msg,  null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
