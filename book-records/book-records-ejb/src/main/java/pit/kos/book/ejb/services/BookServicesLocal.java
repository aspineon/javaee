/**
 * 
 */
package pit.kos.book.ejb.services;

import java.util.List;

import javax.ejb.Local;

import pit.kos.book.ejb.entity.Book;

/**
 * @author Piotr Kosmala
 *20 mar 2016
 *15:30:42
 */@Local
public interface BookServicesLocal {
	Book find(Object id);
	void remove(Book entity);
	Book merge(Book entity);
	void persist(final Book entity);
	List<Book> findAll();
}
