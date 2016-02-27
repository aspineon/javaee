package pit.kos.cdi.user;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import pit.kos.bean.Sklep;
import pit.kos.data.Towar;
import pit.kos.utils.Log;

@Named
@RequestScoped
public class SklepInfo {
	@Inject
	@Log
	private Logger logger;

	@Inject
	private Sklep sklep;

	private Collection<Towar> towary;

	@PostConstruct
	private void init() {
		towary = sklep.getList();
	}
    
	/** dzieki tym znaczniko mozemy pobrac liste towarow #{towary} skrocony zapis
	 * inaczej mozna jeszcze tak  #{sklepInfo.towary}*/
	@Produces
	@Named
	public Collection<Towar> getTowary() {
		return towary;
	}

	/** Sklep sprzedaje towary metoda nasłuchujaca zdarzen fire(towar)
	 * gdy takie zdarzenie jest generowana ta metoda jest wywolywana*/
	@Lock(LockType.READ)
	public void aktualizacjaTowarow(@Observes(notifyObserver = Reception.IF_EXISTS) final Towar towar) {
		logger.info("Ktos kupil towar");
		init();
	}
}
