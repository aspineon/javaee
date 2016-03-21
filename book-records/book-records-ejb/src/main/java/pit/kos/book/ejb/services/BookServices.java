/**
 * 
 */
package pit.kos.book.ejb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pit.kos.book.ejb.dao.DaoInterface;
import pit.kos.book.ejb.entity.Book;

/**
 * @author Piotr Kosmala
 *20 mar 2016
 *15:29:21
 */
@Stateless
public class BookServices implements BookServicesLocal{

	@Inject
	private DaoInterface<Book> bookDao;
	
	@Override
	public Book find(Object id) {
		return bookDao.find(id);
	}

	@Override
	public void remove(Book entity) {
		bookDao.remove(entity);
	}

	@Override
	public Book merge(Book entity) {
		return bookDao.merge(entity);
	}

	@Override
	public void persist(Book entity) {
		bookDao.persist(entity);
	}

	@Override
	public List<Book> findAll() {
		return bookDao.findAll();
	}

}
