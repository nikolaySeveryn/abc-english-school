package nks.abc;

import nks.abc.domain.Book;
import nks.abc.service.BookService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/config/BeanLocation.xml");
		
		BookService service = (BookService) context.getBean("bookService");
		
		Book book1 = new Book();
		book1.setAuthor("Mauroce Druone");
		book1.setName("Les Rois Maudits");
		service.save(book1);
		
		System.out.println("All book");
		for(Book b : service.getAll()){
			System.out.println(b);
		}
		
		
	}

}
