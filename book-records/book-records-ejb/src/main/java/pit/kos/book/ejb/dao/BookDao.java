package pit.kos.book.ejb.dao;

import java.util.Collections;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;

import pit.kos.book.ejb.entity.Author;
import pit.kos.book.ejb.entity.Book;
import pit.kos.book.ejb.utils.Log;

/**
 * @author Piotr Kosmala 18 mar 2016 15:07:04
 */
@Named
public class BookDao extends AbstractDao<Book> implements DaoInterface<Book> {

	private static final long serialVersionUID = 2007815978311610075L;

	@Inject
	@Log
	private Logger logger;

	public BookDao() {
		super(Book.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Book find(Object id) {
		return em.find(aclass, id);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(Book entity) {
		em.remove(em.merge(entity));
		em.flush();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Book merge(Book entity) {
		Book t = em.merge(entity);
		em.flush();
		return t;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persist(Book entity) {
		try{
		 TypedQuery<Author> query =em.createNamedQuery("Author.findauthor", Author.class);
		 query.setParameter("forename", entity.getAuthor().getForename().toLowerCase());
		 query.setParameter("surname", entity.getAuthor().getSurname().toLowerCase());
		 try{
			 Author results = query.getSingleResult();
			 entity.setAuthor(results);
		 }catch(NoResultException e){
			logger.debug("",e);
		 }
		 em.persist(entity);
		}catch(Exception e){
			logger.error("",e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Book> findAll() {
		final CriteriaQuery<Book> criteriaQuery = em.getCriteriaBuilder().createQuery(aclass);
		criteriaQuery.select(criteriaQuery.from(aclass));
		return Collections.unmodifiableList(em.createQuery(criteriaQuery).getResultList());
	}

}
