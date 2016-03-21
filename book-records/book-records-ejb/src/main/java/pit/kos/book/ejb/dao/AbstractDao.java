package pit.kos.book.ejb.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Piotr Kosmala 18 mar 2016 15:02:32
 */
public class AbstractDao<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -7958766800835450388L;

	protected final Class<T> aclass;

	 @PersistenceContext
	protected EntityManager em;

	public AbstractDao(Class<T> aclass) {
		this.aclass = aclass;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}


}
