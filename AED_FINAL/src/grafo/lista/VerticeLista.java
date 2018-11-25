package grafo.lista;

import java.util.*;

import grafo.*;

public class VerticeLista<T> extends Vertice<T> implements IVerticeLista<T> {
	
	private ArrayList<Arista<T>> aristas;

	public VerticeLista(T ele) {
		super(ele);
	}

	@Override
	public void agregarArista(VerticeLista<T> ad, double p, String des) {
		Arista<T> nu = new Arista<>(p, des, ad);
		if (aristas == null) {
			aristas = new ArrayList<>();
		}
		aristas.add(nu);
	}

	@Override
	public void agregarArista(VerticeLista<T> nuevo, String nombre) {
		Arista<T> nu = new Arista<>(nombre, nuevo);
		if (aristas == null) {
			aristas = new ArrayList<>();
		}
		aristas.add(nu);
	}

	@Override
	public ArrayList<VerticeLista<T>> darAdyacentes() {
		if (aristas != null) {
			ArrayList<VerticeLista<T>> adya = new ArrayList<>();
			for (int i = 0; i < aristas.size(); i++) {
				adya.add((VerticeLista) aristas.get(i).getRelacion());
			}
			return adya;
		}
		return null;
	}

	@Override
	public double darPesoArista(VerticeLista<T> vertice) {
		double ret = Double.MAX_VALUE;
		for (int i = 0; i < aristas.size(); i++) {
			if (aristas.get(i).getRelacion().getElemento().equals(vertice.getElemento())) {
				double p = aristas.get(i).getPeso();
				if (p < ret) {
					ret = p;

				}
			}
		}
		return ret;
	}

	@Override
	public void agregarTodos(ArrayList<Arista<T>> nuevas) {
		if (aristas == null) {
			aristas = new ArrayList<>();
		}
		aristas.addAll(nuevas);

	}

	@Override
	public String  darNombreArista(VerticeLista<T> fin) {
		String ret1 = "";
		double ret = Double.MAX_VALUE;
		for (int i = 0; i < aristas.size(); i++) {
			if (aristas.get(i).getRelacion().getElemento().equals(fin.getElemento())) {
				double p = aristas.get(i).getPeso();
				if (p < ret) {
					ret = p;
					ret1 = aristas.get(i).getNombre();

				}
			}
		}
		return ret1;
	}

	/**
	 * @return the aristas
	 */
	public ArrayList<Arista<T>> getAristas() {
		return aristas;
	}

	/**
	 * @param aristas the aristas to set
	 */
	public void setAristas(ArrayList<Arista<T>> aristas) {
		this.aristas = aristas;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(aristas != null){
			return aristas.toString();
		}
		return "Vacio";
	}

}
