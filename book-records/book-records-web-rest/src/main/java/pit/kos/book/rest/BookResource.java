/**
 * 
 */
package pit.kos.book.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import pit.kos.book.ejb.entity.Author;
import pit.kos.book.ejb.entity.Book;
import pit.kos.book.ejb.services.BookServicesLocal;
import pit.kos.book.ejb.utils.Log;

/**
 * @author Piotr Kosmala 20 mar 2016 22:49:23
 */

@Path(value = "/book")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class BookResource{
	
	@Inject
	@Log
	private Logger logger;
	@EJB
	private BookServicesLocal bookServices;
	
	@GET
	public List<Book> getAllBooks() {
		List<Book> list=bookServices.findAll();
		if(list!=null){
			return list;	
		}
		list= new ArrayList<Book>();
		return list;
	}
	@GET
	@Path("/{test}")
	public Response status(@PathParam("test") String msg) {
		String result = "Test : " + msg;
		return Response.status(200).entity(result).build();
	}
	@POST
	@Path("{forname},{surname},{title},{isbn}")
	public Response  addBook(@PathParam("forname") String forname,
						@PathParam("surname") String surname,
						@PathParam("title") String title,
						@PathParam("isbn") String isbn) 
	{
		try{
			Book book= new Book();
			book.setIsbn(isbn);
			book.setTitle(title);
			Author author= new Author();
			author.setForename(forname);
			author.setSurname(surname);
			book.setAuthor(author);
			bookServices.persist(book);
			return Response.ok().build();
		}catch(Exception e){
			logger.info("",e);
			final Entity<String> errorMessage = Entity.json(e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
		}
	}
}
