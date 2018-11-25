package grafo.matriz;

import java.util.ArrayList;
import grafo.*;
import grafo.lista.VerticeLista;

public class RutaMatriz<T> extends Ruta<T> {

	private ArrayList<Vertice<T>> ruta;

	public RutaMatriz() {
		// TODO Auto-generated constructor stub
		super();
		ruta = new ArrayList<>();
	}

	public void agregarTodos(ArrayList<Vertice<T>> ruta) {

		int i = 0;
		while (i < ruta.size()) {
			if (this.ruta.size() < i + 1) {
				this.ruta.add(ruta.get(i));
			} else if (!this.ruta.get(i).equals(ruta.get(i))) {
				this.ruta.set(i, ruta.get(i));
			}
			i++;
		}
	}

	public RutaMatriz(ArrayList<Vertice<T>> ruta, double peso) {
		// TODO Auto-generated constructor stub
		super(peso);
		this.ruta = ruta;
	}

	public void agregarVertice(Vertice<T> nuevo) {
		if (ruta.size() > 0 && !ruta.get(ruta.size() - 1).equals(nuevo))
			ruta.add(nuevo);
		else if (ruta.size() == 0)
			ruta.add(nuevo);
	}

	/**
	 * @return the ruta
	 */
	public ArrayList<Vertice<T>> getRuta() {
		return ruta;
	}

	/**
	 * @param ruta
	 *            the ruta to set
	 */
	public void setRuta(ArrayList<Vertice<T>> ruta) {
		this.ruta = ruta;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String path = "";
		for (int i = 0; i < ruta.size(); i++) {
			path += ruta.get(i).getElemento().toString();
		}
		return path;
	}

}
