package pit.kos.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Towary")
@XmlAccessorType(XmlAccessType.FIELD)
public class Towary {
	@XmlElement(name = "Towar")
	private List<Towar> towary = null;

	public List<Towar> getTowary() {
		return towary;
	}

	public void setTowary(List<Towar> towary) {
		this.towary = towary;
	}

}
