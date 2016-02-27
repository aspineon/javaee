package pit.kos.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Towar")
@XmlAccessorType(XmlAccessType.FIELD)
public class Towar implements Cloneable {

	private double cena;
	private String nazwa;
	private String kod;
	private String kategoria;

	private int ilosc;

	public Towar() {
	}

	public Towar(double cena, String nazwa, String kod) {
		super();
		this.cena = cena;
		this.nazwa = nazwa;
		this.kod = kod;
		this.ilosc = 100;
		this.kategoria = "brak";
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public void setIlosc(int ilosc) {
		this.ilosc = ilosc;
	}

	public void setKategoria(String kategoria) {
		this.kategoria = kategoria;
	}

	public String getNazwa() {
		return nazwa;
	}

	public double getCena() {
		return cena;
	}

	public String getKod() {
		return kod;
	}

	public String getKategoria() {
		return kategoria;
	}

	public int getIlosc() {
		return ilosc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cena);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((kod == null) ? 0 : kod.hashCode());
		result = prime * result + ((nazwa == null) ? 0 : nazwa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Towar other = (Towar) obj;
		if (Double.doubleToLongBits(cena) != Double
				.doubleToLongBits(other.cena))
			return false;
		if (kod == null) {
			if (other.kod != null)
				return false;
		} else if (!kod.equals(other.kod))
			return false;
		if (nazwa == null) {
			if (other.nazwa != null)
				return false;
		} else if (!nazwa.equals(other.nazwa))
			return false;
		return true;
	}

	public void decrement() {
		ilosc--;
	}

	
	@Override
	public String toString() {
		return "Towar [cena=" + cena + ", nazwa=" + nazwa + ", kod=" + kod
				+ ", kategoria=" + kategoria + ", ilosc=" + ilosc + "]";
	}

	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
