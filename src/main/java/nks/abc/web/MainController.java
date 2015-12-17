package nks.abc.web;

import java.util.List;

import javax.annotation.Resource;

import nks.abc.domain.Book;
import nks.abc.service.BookService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class MainController {

	@Resource(name = "bookService")
	private BookService bookService;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String getPerson(Model model) {
		List<Book> books = bookService.getAll();
		model.addAttribute("books", books);
		return "bookspage";
	}

	@RequestMapping(value = "/books/add", method = RequestMethod.GET)
	public String getAdd(Model model) {
		model.addAttribute("book", new Book());
		return "addpage";
	}

	@RequestMapping(value = "/books/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("book") Book book) {
		bookService.add(book);
		return "addedpage";
	}

	@RequestMapping(value = "/books/delete")
	public String delete(
			@RequestParam(value = "id", required = true) Integer id, Model model) {
		bookService.delete(id);
		model.addAttribute("id", id);
		return "deletepage";
	}

	@RequestMapping(value = "/books/edit", method = RequestMethod.GET)
	public String getEdit(
			@RequestParam(value = "id", required = true) Integer id, Model model) {

		model.addAttribute("book", bookService.get(id));
		return "editpage";
	}

	@RequestMapping(value = "/books/edit", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("book") Book book,
			@RequestParam(value = "id", required = true) Integer id, Model model) {
		book.setId(id);
		bookService.update(book);
		model.addAttribute("id", id);
		return "editedpage";
	}

}
