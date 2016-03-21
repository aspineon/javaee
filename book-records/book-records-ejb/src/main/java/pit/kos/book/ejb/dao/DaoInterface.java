/**
 * 
 */
package pit.kos.book.ejb.dao;

import java.util.List;

/**
 * @author Piotr Kosmala 18 mar 2016 15:12:31
 * @param <T>
 */

public interface DaoInterface<T> {

	T find(Object id);

	void remove(T entity);

	T merge(T entity);

	void persist(final T entity);

	List<T> findAll();
}
