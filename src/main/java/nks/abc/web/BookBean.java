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
import nks.abc.web.common.enumeration.EditingMode;
import nks.abc.web.common.util.FacesUtilit;

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
	private BookDTO book = new BookDTO.NullBookDTO();
	
	public void add () {
		mode = EditingMode.ADD;
		book = new BookDTO(null, new String(), new String());
	}
	
	public void edit(BookDTO book) {
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
		this.book = new BookDTO.NullBookDTO();
		utilit.addMessage(FacesMessage.SEVERITY_INFO, "скасовано");
	}
	
	public void delete(BookDTO book) {
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
	
	public List<BookDTO> getList() {
		try {
			return bookService.getAll();
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			e.printStackTrace();
			return new ArrayList<BookDTO>();
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
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

	public EditingMode getMode() {
		return mode;
	}

	public void setMode(EditingMode mode) {
		this.mode = mode;
	}
	
}
