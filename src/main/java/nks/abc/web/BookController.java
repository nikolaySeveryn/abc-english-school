package nks.abc.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import nks.abc.domain.Book;


@ManagedBean(name="bookBean")
public class BookController {
	
	public List<Book> getList(){
		Book book1 = new Book();
		book1.setAuthor("Mauroce Druone");
		book1.setName("Les Rois Maudits");
		List<Book> books = new ArrayList<Book>();
		books.add(book1);
		return books;
	}
}
