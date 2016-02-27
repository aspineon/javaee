package pit.kos.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;

import pit.kos.data.Towar;
import pit.kos.data.Towary;
import pit.kos.utils.Log;

@Singleton
@Startup
@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
public class Sklep {
	@Inject @Log
	private Logger logger;
	private final String saveDirFileXMlPath="H:\\serwery\\wildfly\\towary.xml";
	/** Lista dostepnych towarow w sklepie */
	private List<Towar> listaTowarow; //
	@Inject
	private Event<Towar> event;
	@Inject
	private Event<Towary> towaryevent;
	@Resource
	private TimerService timerService; 										// time servie by zakonczyc szedulera
	/**tworzenie listy towarow*/
	@PostConstruct
	private void init()  {
		JAXBContext jaxbContext;
		try {
			Path path = Paths.get(saveDirFileXMlPath);
			jaxbContext = JAXBContext.newInstance(Towary.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream in = null;
			
			if (Files.exists(path)) {
				if (Files.isRegularFile(path)) {
					try{
						in = Files.newInputStream(path);
						logger.info("Open from history");
					}catch(IOException e){
						logger.error("Blad otwarcia sprawdz program");
						ClassLoader classloader = Thread.currentThread().getContextClassLoader();
						in = classloader.getResourceAsStream("towary.xml");
					}
				}
			}
			if (Files.notExists(path)) {
				logger.info("Open from proeject");
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				in = classloader.getResourceAsStream("towary.xml");
			 }
			Towary towary = (Towary) jaxbUnmarshaller.unmarshal(in);
			listaTowarow = towary.getTowary();
			in.close();	
		} catch (JAXBException| IOException e) {
			listaTowarow = new ArrayList<>();
			logger.error("error load file");
		}
	}
	
	@Lock(LockType.WRITE)
	public boolean sprzedajTowar(String kod) {
		if (!listaTowarow.isEmpty()) {
			for (Towar t : listaTowarow) {
				if (t.getKod().equals(kod)) {
					if (t.getIlosc() > 0) {
						t.decrement();
						event.fire(t);
						return true;
					}
				}
			}
		}
		return false;
	}

	/** Serwis sprzedajacy */
	@Lock(LockType.WRITE)
	@Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
	private void autoSprzedaz() {
		logger.info(" Scheduler");
		/**
		 * Sprawdzamy czy istnieje towar na stanie
		 **/
		if (!listaTowarow.isEmpty()) {
			/** Losujemy towar do sprzedania */
			int idTowar = new Random().nextInt(((listaTowarow.size() - 1)) + 1);
			/** Pobieramy towar i sprawdzamy jego ilosc */
			Towar t = listaTowarow.get(idTowar);
			/** Jesli towar jest na stanie mozemy go kupic */
			if (t.getIlosc() > 0) {
				/** dekrementacja */
				t.decrement();
				/**
				 * Tworzymy zdarzenie dla klasy Sklep info ktora uaktualni liste towarow
				 */
				event.fire(t);
			} else {
				logger.info("Brak towaru");
				/** Brak tego towaru ktos juz go kupil powiadamiamy ze trzeba  odswierzy liste towarow
				 */
				event.fire(t);
				listaTowarow.remove(idTowar);
			}
		} else {
			cancelTimers();
		}
	}

	@Lock(LockType.WRITE)
	public void zapiszDane() throws JAXBException {
		Towary towary = new Towary();
		towary.setTowary(listaTowarow);
		JAXBContext jaxbContext = JAXBContext.newInstance(Towary.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// Marshal the towary list in file
		jaxbMarshaller.marshal(towary,new File(saveDirFileXMlPath));
		towaryevent.fire(towary);
	}

	/**
	 * Pobranie listy dostepnych towarow zwarcamy liste towarow nie do
	 * modyfikacji
	 */
	@Lock(LockType.READ)
	public Collection<Towar> getList() { 									// dla klienta
		return Collections.unmodifiableList(listaTowarow);
	}
	@Lock(LockType.READ)
	public Collection<Towar> getListToUpdate() { 								// dla admina
		return listaTowarow;
	}
	
	@Lock(LockType.READ)
	public Towar getTowarByKod(String kod) throws CloneNotSupportedException { // dla admina do aktualizacji
		Optional<Towar> serach= listaTowarow.            // wyrazenie lambda
				stream().      							 // lista jak strumien
				parallel(). 							  // operacja rownolegla
				filter(towar->towar.getKod().equals(kod)) // znajdz towar gdzie kod jest rowny
				.findFirst();							 // znajdz pierwszy
		Towar towar=serach.get();                        // pobierz z listy wynikow
		return (Towar) towar.clone();                    // sklonuj i zwroc
	}
	@Lock(LockType.WRITE)
	public void aktualizujTowar(Towar t)  { 									// dla admina do aktualizacji
		Optional<Towar> serach= listaTowarow.stream().parallel()
				.filter(towar->towar.getKod().equals(t.getKod())).findFirst();
		
		Towar towar=serach.get();
		logger.info("Nowe ustawienia "+t.toString());
		logger.info("Znaleziony "+towar.toString());
		
		towar.setNazwa(t.getNazwa());
		towar.setCena(t.getCena());;
		towar.setIlosc(t.getIlosc());
		towar.setKategoria(t.getKategoria());
		towar.setKod(t.getKod());
		event.fire(towar);
	}
	
	/** Gdy brak towaru konczymy prace metody autoSprzedaz */
	private void cancelTimers() {
		logger.info("cancelTimers");
		for (Timer timer : timerService.getTimers()) {
			timer.cancel();
		}
	}
}
