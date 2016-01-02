package nks.abc.domain.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import nks.abc.domain.dto.BookDTO;
import nks.abc.domain.dto.BookDTO.NullBookDTO;
import nks.abc.domain.entity.Book;

public class BookDTOConvertor {
	
	public static BookDTO toDTO(Book entity){
		BookDTO dto = new BookDTO.NullBookDTO();
		dto =  new BookDTO(entity.getId(), entity.getName(), entity.getAuthor());
		return dto;
	}
	
	public static List<BookDTO> toDTO(List<Book> entities){
		List<BookDTO> res = new ArrayList<BookDTO>();
		for(Book entity: entities){
			res.add(toDTO(entity));
		}
		return res;
	}
	
	public static Book toEntity(BookDTO dto) {
		Book entity = new Book();
		entity.setId(dto.getId());
		entity.setAuthor(dto.getAuthor());
		entity.setName(dto.getName());
		return entity;
	}
	
	public static List<Book> toEntity(List<BookDTO> dtos) {
		List<Book> res = new ArrayList<Book>();
		for(BookDTO dto : dtos) {
			res.add(toEntity(dto));
		}
		return res;
	}
}
