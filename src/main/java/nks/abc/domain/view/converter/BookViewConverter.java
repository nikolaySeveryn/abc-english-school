package nks.abc.domain.view.converter;

import java.util.ArrayList;
import java.util.List;

import nks.abc.domain.entity.Book;
import nks.abc.domain.view.object.BookView;
import nks.abc.domain.view.object.BookView.NullBookDTO;

public class BookViewConverter {
	
	public static BookView toView(Book entity){
		BookView dto = new BookView.NullBookDTO();
		dto =  new BookView(entity.getId(), entity.getName(), entity.getAuthor());
		return dto;
	}
	
	public static List<BookView> toView(List<Book> entities){
		List<BookView> res = new ArrayList<BookView>();
		for(Book entity: entities){
			res.add(toView(entity));
		}
		return res;
	}
	
	public static Book toEntity(BookView dto) {
		Book entity = new Book();
		entity.setId(dto.getId());
		entity.setAuthor(dto.getAuthor());
		entity.setName(dto.getName());
		return entity;
	}
	
	public static List<Book> toEntity(List<BookView> dtos) {
		List<Book> res = new ArrayList<Book>();
		for(BookView dto : dtos) {
			res.add(toEntity(dto));
		}
		return res;
	}
}
