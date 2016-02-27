package pit.kos.cdi.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;




import org.slf4j.Logger;

import pit.kos.bean.Sklep;
import pit.kos.utils.Koszyk;
import pit.kos.utils.Log;

@Named
@SessionScoped
public class Klient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject @Log
	private Logger logger;

	@Inject
	private Sklep sklep;

	private Koszyk<String> koszyk;

	@PostConstruct
	private void init() {
		koszyk = new Koszyk<>();
	}

	public void dodaj_DoKoszyka(ActionEvent event) {
		try {
			String kod = (String) event.getComponent().getAttributes().get("kod_dodania");
			kod = kod.trim();
			koszyk.dodajTowar(kod);
		} catch (Exception e) {
			wyswietlInformacje("Przepraszamy wystopil blad sproboj pozniej");
		}
	}

	public void usun_ZKoszyka(ActionEvent event) {
		try {
			String kod = (String) event.getComponent().getAttributes().get("kod_usuniecia");
			kod = kod.trim();
			koszyk.usunTowar(kod);
		} catch (Exception e) {
			wyswietlInformacje("Przepraszamy wystopil blad sproboj pozniej");
		}
	}

	@Produces
	@Named
	public Collection<String> getTowaryWKoszyku() {
		return Collections.unmodifiableList(koszyk.getTowaryWKoszyku());
	}

	public void zrealizujZamowienie() {
		if (!koszyk.pustyKoszyk()) {
			StringBuilder str = new StringBuilder();
			try {
				for (String kod : koszyk.getTowaryWKoszyku()) {

					try {
						Integer.parseInt(kod);
						if (sklep.sprzedajTowar(kod)) {
							str.append("Gratulacje zakupu towaru ").append(kod);

						} else {
							str.append(
									"Przepraszamy wystopil problem z zakupem towaru o kodzie ")
									.append(kod);
						}

					} catch (Exception e) {
						str.append("Towaru o kodzie " + kod + " nie zakupiono.");

					}
				}
				koszyk = new Koszyk<>();
				wyswietlInformacje(str.toString());
				return;
			} catch (Exception e) {
				wyswietlInformacje(str.toString());
			}
		} else {
			wyswietlInformacje("Koszyk jest pusty");
		}
	}
	
	

	public void wyswietlInformacje(String info) {
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, info,
				info);
		FacesContext.getCurrentInstance().addMessage(null, m);
	}

}
