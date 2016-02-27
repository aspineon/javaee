package pit.kos.utils;

import java.util.ArrayList;
import java.util.List;

public class Koszyk<E> {
	
	private List<E> towaryWKoszyku;
	
	public Koszyk(){
		towaryWKoszyku= new ArrayList<E>();
	}
	
	public void dodajTowar(E towar){
		towaryWKoszyku.add(towar);
	}
	public void usunTowar(E towar){
		towaryWKoszyku.remove(towar);
	}
	public List<E> getTowaryWKoszyku(){
		return towaryWKoszyku;
	}
	public boolean pustyKoszyk(){
		return towaryWKoszyku.isEmpty();
	}
}
