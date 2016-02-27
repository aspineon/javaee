package pit.kos.cdi.admin;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import pit.kos.bean.Sklep;
import pit.kos.data.Towar;
import pit.kos.utils.Log;


@Named
@SessionScoped
public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Sklep sklep;
	
	 
	
	
	private Towar towarDoAktualizacji;
	private boolean edytujTowar;

	@Inject
	@Log
	private Logger logger;

	public void zapiszDane() {
		try {
			sklep.zapiszDane();
		} catch (Exception e) {
			logger.info("", e);
			wyswietlInformacje("Wystopil bld przetwarzania");
		}

	}

	public void edytujTowar(ActionEvent event) {
		try {
			String kod = (String) event.getComponent().getAttributes().get("kod_edycji");
			kod = kod.trim();
			Integer.parseInt(kod);
			towarDoAktualizacji=sklep.getTowarByKod(kod);
			edytujTowar=true;
		;
		} catch (Exception e) {
			wyswietlInformacje("Przepraszamy wystopil blad sproboj pozniej");
		}
	}
	public void aktualizujTowar() {
		try {
			sklep.aktualizujTowar(towarDoAktualizacji);
			wyswietlInformacje("Aktualizacja udana");
			anuluj_AktualizacjeTowaru();
		} catch (Exception e) {
			wyswietlInformacje("Przepraszamy wystopil blad sproboj pozniej");
		}
	}
	public void anuluj_AktualizacjeTowaru() {
		 edytujTowar=false;
		 towarDoAktualizacji=null;
	}
	

	public void dodajDoStanu(ActionEvent event) {

		/*
		 * try { String kod = (String)
		 * event.getComponent().getAttributes().get("kod"); kod=kod.trim();
		 * Integer.parseInt(kod); sklep.dodajTowar(kod, sztuki); } catch
		 * (Exception e) {
		 * wyswietlInformacje("Przepraszamy wystopil blad sproboj pozniej"); }
		 */

	}

	public void wyswietlInformacje(String info) {
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, info,info);
		FacesContext.getCurrentInstance().addMessage(null, m);
	}

	/**
	 * @return the edytujTowar
	 */
	public boolean isEdytujTowar() {
		return edytujTowar;
	}

	/**
	 * @param edytujTowar the edytujTowar to set
	 */
	public void setEdytujTowar(boolean edytujTowar) {
		this.edytujTowar = edytujTowar;
	}

	
	
	public Towar getTowarDoAktualizacji() {
		return towarDoAktualizacji;
	}

	/**
	 * @param towarDoAktualizacji the towarDoAktualizacji to set
	 */
	public void setTowarDoAktualizacji(Towar towarDoAktualizacji) {
		this.towarDoAktualizacji = towarDoAktualizacji;
	}
	

}
