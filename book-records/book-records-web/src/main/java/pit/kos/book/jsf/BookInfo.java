package pit.kos.book.jsf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import pit.kos.book.ejb.entity.Book;
import pit.kos.book.ejb.services.BookServicesLocal;
import pit.kos.book.ejb.utils.Log;

/**
 * @author Piotr Kosmala 18 mar 2016 17:17:31
 */

@Named
@RequestScoped
public class BookInfo implements Serializable {

	private static final long serialVersionUID = 1547031612818939534L;

	private List<Book> listBook;
	@Inject
	@Log
	private Logger logger;
	@EJB
	private BookServicesLocal bookServices;

	@Produces
	@Named
	public List<Book> getListBook() {
		try {
			listBook = bookServices.findAll();
			return listBook;
		} catch (Exception e){
			logger.error("", e);
		}
		return null;
	}
}
