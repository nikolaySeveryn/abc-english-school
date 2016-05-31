package nks.abc.domain.view.object;

import javax.faces.bean.ManagedBean;
import javax.validation.constraints.NotNull;

@ManagedBean()
public class BookView {
	
	public static class NullBookDTO extends BookView {
		
		public NullBookDTO(){
			super(0,new String(),new String());
		}
	}
	
	private Integer id;
	private String name;
	private String author;
	
	
	public BookView(Integer id, String name, String author) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookView other = (BookView) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", name=" + name + ", author=" + author
				+ "]";
	}
	
}
