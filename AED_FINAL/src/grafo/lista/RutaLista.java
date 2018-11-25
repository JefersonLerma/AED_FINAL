package grafo.lista;

import java.util.ArrayList;
import grafo.*;

public class RutaLista<T> extends Ruta<T>{

	private ArrayList<VerticeLista<T>> ruta;
	
	public RutaLista() {
		super();
		// TODO Auto-generated constructor stub
		ruta = new ArrayList<>();
	}

	public RutaLista(ArrayList<VerticeLista<T>> ruta, double peso) {
		super(peso);
		this.ruta = ruta;
	}
	
	public void agregarTodos(ArrayList<VerticeLista<T>> ruta){
		
		int i = 0;
		while(i < ruta.size()){
			if(this.ruta.size() < i+1){
				this.ruta.add(ruta.get(i));
			}
			else if(!this.ruta.get(i).equals(ruta.get(i))){
				this.ruta.set(i, ruta.get(i));
			}
			i++;
		}
	}
	
	public void agregarVertice(VerticeLista<T> nuevo){
		if(ruta.size() > 0 && !ruta.get(ruta.size()-1).equals(nuevo))
			ruta.add(nuevo);
		else if(ruta.size() == 0)
			ruta.add(nuevo);
	}

	/**
	 * @return the ruta
	 */
	public ArrayList<VerticeLista<T>> getRuta() {
		return ruta;
	}

	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(ArrayList<VerticeLista<T>> ruta) {
		this.ruta = ruta;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String path = "";
		for (int i = 0; i < ruta.size(); i++) {
			path+= ruta.get(i).getElemento().toString();
					
		}
		return path;
	}

}
