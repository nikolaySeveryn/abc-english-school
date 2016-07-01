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

import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.core.util.FacesUtilit;
import nks.abc.domain.view.object.BookView;
import nks.abc.service.BookService;
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
	private FacesUtilit utilit;
	
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
				utilit.addMessage(FacesMessage.SEVERITY_INFO, "Added");
			}
			else if(mode.equals(EditingMode.EDIT)){
				System.out.println("save editing");
				bookService.update(book);
				utilit.addMessage(FacesMessage.SEVERITY_INFO, "Updated");
			}
			else {
				utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			}
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			e.printStackTrace();
		}
	}

	public void cancel(){
		this.mode = EditingMode.NONE;
		this.book = new BookView.NullBookDTO();
		utilit.addMessage(FacesMessage.SEVERITY_INFO, "скасовано");
	}
	
	public void delete(BookView book) {
		try {
			bookService.delete(book);
			utilit.addMessage(FacesMessage.SEVERITY_WARN, "Deleted");
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			e.printStackTrace();
		}	
	}
	
	public List<BookView> getList() {
		try {
			return bookService.getAll();
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
			return new ArrayList<BookView>();
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
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
