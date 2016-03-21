/**
 * 
 */
package pit.kos.book.ejb.test;

/**
 * @author Piotr Kosmala
 *20 mar 2016
 *01:06:37
 */


import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pit.kos.book.ejb.dao.AbstractDao;
import pit.kos.book.ejb.entity.Author;
import pit.kos.book.ejb.entity.Book;
import pit.kos.book.ejb.services.BookServices;
import pit.kos.book.ejb.services.BookServicesLocal;
import pit.kos.book.ejb.utils.Log;

@RunWith(Arquillian.class)
public class TestBookServices {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TestBookServices.class);
	@EJB
	private BookServicesLocal services;
	
	 @Deployment
	    public static WebArchive createDeployment() {
	        return ShrinkWrap.create(WebArchive.class, "test2.war")
	            .addPackage(AbstractDao.class.getPackage())
	             .addPackage(Author.class.getPackage())
	             .addPackage(BookServices.class.getPackage())
	              .addPackage(Log.class.getPackage())
	               .addAsWebInfResource("wildfly-ds.xml")
	            .addAsResource("test-persistence.xml", "META-INF/persistence.xml") 
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	    }
	    
	 @Test
		public void testPersist() {
			Book book= new Book();
	    	book.setIsbn("ISBNISBNISBN");
	    	book.setTitle("Example Title");
	    	Author author= new Author();
	    	author.setForename("Piotr");
	    	author.setSurname("Kos");
	    	book.setAuthor(author);
	    	services.persist(book);
	    	Book abook=services.find(book.getId_book());
	    	Assert.assertEquals(abook,book);
	        Assert.assertEquals(abook.getIsbn(),book.getIsbn());
	        Assert.assertEquals(abook.getTitle(),book.getTitle());
	        Assert.assertEquals(abook.getId_book(),book.getId_book());
		}

		@Test
		public void findEdit() {
			Book book= new Book();
	    	book.setIsbn("ISBNISBNISBN");
	    	book.setTitle("Example Title");
	    	
	    	Author author= new Author();
	    	author.setForename("APiotr");
	    	author.setSurname("Kos");
	    	book.setAuthor(author);
	    	
	    	services.persist(book);
	    	
	    	Book abook=services.find(book.getId_book());
	    	abook.setTitle("new title");
	    	abook=services.merge(abook);
	    	
	    	Assert.assertNotEquals(abook.getTitle(),book.getTitle());
	        Assert.assertEquals(abook.getIsbn(),book.getIsbn());
	        Assert.assertEquals(abook.getId_book(),book.getId_book());
		}


		@Test
		public void testFindAll() {
			Author author= new Author();
	    	author.setForename("APiotr");
	    	author.setSurname("Kos");
			for(int i=0;i<10;i++){
				Book book= new Book();
		    	book.setIsbn("ISBNISBNISBN"+i);
		    	book.setTitle("Example Title"+i);
		    	book.setAuthor(author);
		    	services.persist(book);
			}
			List<Book> abook=services.findAll();
			Assert.assertNotNull(abook);
		}

		@Test
		public void testRemove() {
			Book book= new Book();
	    	book.setIsbn("ISBNISBNISBN");
	    	book.setTitle("Example Title");
	    	Author author= new Author();
	    	author.setForename("Piotr");
	    	author.setSurname("Kos");
	    	book.setAuthor(author);
	    	services.persist(book);
	    	book=services.merge(book);
	    	services.remove(book);
	    	book = services.find(book.getId_book());
			Assert.assertNull(book);
		}
}