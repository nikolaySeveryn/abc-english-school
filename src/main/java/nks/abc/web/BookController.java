package nks.abc.web;

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
		System.out.println("add");
		mode = EditMode.ADD;
		book = new BookDTO(null, new String(), new String());
	}
	
	public void edit(BookDTO book) {
		System.out.println("edit");
		mode = EditMode.EDIT;
		this.book = book;
	}
	
	public void save() {
		System.out.println("save");
		String msg = new String();
		if(mode.equals(EditMode.ADD)){
			System.out.println("save adding");
			bookService.save(book);
			msg = "Додано";
		}
		else if(mode.equals(EditMode.EDIT)){
			System.out.println("save editing");
			bookService.update(book);
			msg = "Оновлено";
		}
		else {
			addMessage(FacesMessage.SEVERITY_ERROR,"Помилка");
			return ;
		}
		addMessage(FacesMessage.SEVERITY_INFO,msg);
	}

	public void cancel(){
		this.mode = EditMode.NONE;
		this.book = new BookDTO.NullBookDTO();
		addMessage(FacesMessage.SEVERITY_INFO, "скасовано");
	}
	
	public void delete(BookDTO book) {
		bookService.delete(book);
		addMessage(FacesMessage.SEVERITY_WARN, "видалено");
	}
	
	public List<BookDTO> getList() {
		return bookService.getAll();
	}
	
	public BookDTO getBook() {
		System.out.println("get book: " + book);
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
