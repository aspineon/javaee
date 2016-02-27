package pit.kos.cdi.user;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import pit.kos.bean.Sklep;
import pit.kos.data.Towar;
import pit.kos.data.Towary;
import pit.kos.utils.Log;


@Named
@SessionScoped
public class SklepAdminInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Sklep sprzedaje towary */
	
	@Inject @Log
	private Logger logger;
	
	@Inject
	private Sklep sklep;
	
	private Collection<Towar> towary;
	
	@PostConstruct
	private void init(){
		towary=sklep.getListToUpdate();
		logger.info("SklepAdminInfo init");
	}
	
	@Produces
	@Named
	@Lock(LockType.READ)
	public Collection<Towar> getListToUpdate() {
		return towary;
	}
	
	
	@Lock(LockType.READ)
	public void aktualizacjaTowarowAdmin(@Observes(notifyObserver = Reception.IF_EXISTS) final Towary towtowaryar){
		logger.info("Administrator zmienil ustawienia");
		init();
	}
}
