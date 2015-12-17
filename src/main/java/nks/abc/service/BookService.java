package nks.abc.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import nks.abc.domain.Book;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;


@Service("bookService")
@Transactional
public class BookService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public List<Book> getAll(){
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("FROM Book");
		return q.list();
	}
	
	public Book get(Integer id){
		Session session = sessionFactory.getCurrentSession();
		Book book =  (Book) session.get(Book.class, id);
		return book;
	}
	
	public void add (Book book){
		Session session = sessionFactory.getCurrentSession();
		session.save(book);
	}
	
	public void delete(Book book) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(book);
	}
	
	public void delete(Integer id){
		Session session = sessionFactory.getCurrentSession();
		Book book = get(id);
		session.delete(book);
	}
	
	public void update (Book book){
		Session session = sessionFactory.getCurrentSession();
		Book existenBook = get(book.getId());
		
		existenBook.setAuthor(book.getAuthor());
		existenBook.setName(book.getName());
		
		session.save(book);
	}
}
