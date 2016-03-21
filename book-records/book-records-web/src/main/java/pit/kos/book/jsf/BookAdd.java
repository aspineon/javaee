/**
 * 
 */
package pit.kos.book.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import pit.kos.book.ejb.entity.Book;
import pit.kos.book.ejb.services.BookServicesLocal;
import pit.kos.book.ejb.utils.Log;

/**
 * @author Piotr Kosmala 18 mar 2016 19:26:47
 */
@Named
@javax.faces.view.ViewScoped
public class BookAdd implements Serializable {

	
	private static final String ERROR_ADD_BOOK = "error";

	private static final String SUCCES_ADD_BOOK = "succes";

	private static final long serialVersionUID = -278943616206899390L;

	private Book book;

	@Inject
	@Log
	private Logger logger;
	@EJB
	private BookServicesLocal bookServices;
	
	
	@PostConstruct
	private void init(){
		book= new Book();
		logger.info("init");
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String addBook() {
		try{
			bookServices.persist(book);
			return SUCCES_ADD_BOOK;
		}catch(Exception e){
			logger.error("",e);
			return ERROR_ADD_BOOK;
		}

	}
}
