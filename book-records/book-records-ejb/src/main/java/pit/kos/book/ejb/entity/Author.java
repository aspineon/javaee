/**
 * 
 */
package pit.kos.book.ejb.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Piotr Kosmala
 *18 mar 2016
 *20:02:23
 */
@Entity(name="author")
@Table(name="author")
@NamedQueries(value = {
		@NamedQuery(name="Author.findauthor", query="SELECT a FROM author a where lower(a.forename)=:forename and lower(a.surname)=:surname")
})
public class Author implements Serializable {
	
	private static final long serialVersionUID = 2680439161315466265L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_author;
	
	@NotNull
	@Column(name = "forename")
	@Size(min=1,max=40,message="Size forename must be between 1 and 40")
	private String forename;
	
	@NotNull
	@Column(name = "surname")
	@Size(min=2,max=40,message="Size surname must be between 2 and 40")
	private String surname;
	
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="author")
	private List<Book> books;
	
	
	public Author() {
		
	}
	public Long getId_author() {
		return id_author;
	}
	public void setId_author(Long id_author) {
		this.id_author = id_author;
	}
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((forename == null) ? 0 : forename.hashCode());
		result = prime * result
				+ ((id_author == null) ? 0 : id_author.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		Author other = (Author) obj;
		if (forename == null) {
			if (other.forename != null)
				return false;
		} else if (!forename.equals(other.forename))
			return false;
		if (id_author == null) {
			if (other.id_author != null)
				return false;
		} else if (!id_author.equals(other.id_author))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
	
	
	
	
	
}
