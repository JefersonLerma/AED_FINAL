package grafo.matriz;

import java.util.*;
import grafo.*;

public class GrafoMatriz<T> implements IGrafoMatriz<T> {
	
	private ArrayList<ArrayList<Arista<T>>> aristas;

	private ArrayList<Vertice<T>> vertices;

	public GrafoMatriz() {
		aristas = new ArrayList<>();
		vertices = new ArrayList<>();
	}

	@Override
	public IVertice<T> crearNodo(T vertice, T[] adyacentes, String[] nombres, double[] pesos, int direccion) {
		IVertice<T> nuevo = null;
		if (adyacentes != null) {
			int index = darIndice(vertice);
			if (index == -1) {
				vertices.add(new Vertice<T>(vertice));
				index = vertices.size() - 1;
				Arista<T> n = new Arista<>(Double.MAX_VALUE, "No name");
				ArrayList<Arista<T>> aris = new ArrayList<>();
				if (aristas.size() > 0) {
					ArrayList<Arista<T>> uis = aristas.get(aristas.size() - 1);
					for (int i = 0; i < uis.size(); i++) {
						aris.add(n);
					}
				}
				aristas.add(aris);
				for (int i = 0; i < aristas.size(); i++) {
					ArrayList<Arista<T>> f = aristas.get(i);
					if (f == null) {
						f = new ArrayList<>();
					}
					f.add(n);
				}
			}
			aristas.get(index).set(index, new Arista<>(0, "Don't move"));
			for (int i = 0; i < adyacentes.length; i++) {
				int index1 = darIndice(adyacentes[i]);
				if (index1 == -1) {
					vertices.add(new Vertice<>(adyacentes[i]));
					index1 = vertices.size() - 1;
					Arista<T> n = new Arista<>(Double.MAX_VALUE, "No name");
					ArrayList<Arista<T>> aris = new ArrayList<>();
					if (aristas.size() > 0) {
						ArrayList<Arista<T>> uis = aristas.get(aristas.size() - 1);
						for (int k = 0; k < uis.size(); k++) {
							aris.add(n);
						}
					}
					aristas.add(aris);
					for (int j = 0; j < aristas.size(); j++) {
						ArrayList<Arista<T>> f = aristas.get(j);
						if (f == null) {
							f = new ArrayList<>();
						}
						f.add(n);
					}
				}
				Arista<T> nueva = new Arista<>(pesos[i], nombres[i]);
				Arista<T> anterior = aristas.get(index).get(index1);
				if (anterior.getPeso() > nueva.getPeso()) {
					aristas.get(index).set(index1, nueva);
				}
				if (direccion == NO_DIRIGIDO) {
					Arista<T> anteriorS = aristas.get(index1).get(index);

					if (anteriorS.getPeso() > nueva.getPeso()) {
						aristas.get(index1).set(index, nueva);
					}
				}

				aristas.get(index1).set(index1, new Arista<>(0, "Don't move"));

			}

		}

		return nuevo;
	}

	@Override
	public IVertice<T> crearNodo(T vertice, T[] adyacentes, String[] nombres, int direccion) {

		return null;
	}

	@Override
	public RutaMatriz<T> dijsktraParcial(T ini, T f) {
		Vertice<T> fin = get(f);
		if (fin != null) {
			int index = -1;
			for (int i = 0; i < vertices.size(); i++) {
				if (vertices.get(i).getElemento().equals(fin.getElemento())) {
					index = i;
					break;
				}
			}
			if (index >= 0) {
				Vertice<T> inicio = get(ini);
				RutasMatriz<T> ca = dijsktra(inicio);
				RutaMatriz<T> salida = ca.getRutas()[index];
				return salida;
			} else {
				return null;
			}

		} else {
			return null;
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public RutaMatriz<T>[][] floydWarshall() {
		int tam = vertices.size();
		RutaMatriz<T>[][] caminos = new RutaMatriz[tam][tam];
		for (int i = 0; i < caminos.length; i++) {
			for (int j = 0; j < caminos.length; j++) {

				Arista<T> ar = aristas.get(i).get(j);
				ArrayList<Vertice<T>> c = new ArrayList<>();
				double pes = ar.getPeso();
				if (pes != Double.MAX_VALUE) {
					c.add(vertices.get(i));
					if (i != j) {
						c.add(vertices.get(j));
					}
				}
				RutaMatriz<T> nuevo = new RutaMatriz<>(c, pes);
				caminos[i][j] = nuevo;
			}
		}
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < caminos.length; j++) {
				if (j != i) {
					for (int j2 = 0; j2 < caminos.length; j2++) {
						if (j2 != i && j != j2) {
							double pesoFp = caminos[i][j2].getPeso();
							double pesoFc = caminos[j][i].getPeso();
							if (pesoFc == Double.MAX_VALUE) {
								break;
							}
							double peso = caminos[j][j2].getPeso();
							if (pesoFp != Double.MAX_VALUE) {
								double suma = pesoFp + pesoFc;
								if (suma < peso) {
									RutaMatriz<T> f = caminos[j][i];
									ArrayList<Vertice<T>> p = f.getRuta();
									RutaMatriz<T> s = caminos[i][j2];
									ArrayList<Vertice<T>> se = s.getRuta();
									ArrayList<Vertice<T>> aux = new ArrayList<>();
									aux.addAll(se);
									if (aux.size() > 1) {
										aux.remove(0);

									}
									ArrayList<Vertice<T>> nueva = new ArrayList<>();
									nueva.addAll(p);
									nueva.addAll(aux);
									RutaMatriz<T> nuevos = new RutaMatriz<>(nueva, suma);
									caminos[j][j2] = nuevos;
								}
							}

						}
					}
				}

			}
		}

		return caminos;
	}

	@Override
	public ArrayList<T> BFS(T ele) {
		Vertice<T> el = get(ele);
		ArrayList<T> ret = new ArrayList<>();
		ArrayDeque<Vertice<T>> aux = new ArrayDeque<>();
		ArrayList<Vertice<T>> mar = new ArrayList<>();
		if (ele != null) {
			aux.add(el);
			while (!aux.isEmpty()) {
				Vertice<T> tmp = aux.pop();
				if (tmp.marcado() != IVertice.SENTIDO) {
					tmp.marcar();
					ArrayList<Vertice<T>> adr = getAdyacentes(tmp);
					if (adr != null && adr.size() > 0) {
						aux.addAll(adr);

					}
					ret.add(tmp.getElemento());
					mar.add(tmp);
				}

			}

		}
		for (IVertice<T> t : mar) {
			t.marcar();
		}
		return ret;
	}

	@Override
	public ArrayList<T> DFS(T ele) {
		Vertice<T> el = get(ele);
		ArrayList<T> ret = new ArrayList<>();
		ArrayList<Vertice<T>> mar = new ArrayList<>();
		Stack<Vertice<T>> aux = new Stack<>();
		if (ele != null) {
			aux.add(el);
			while (!aux.isEmpty()) {
				Vertice<T> tmp = aux.pop();
				if (tmp.marcado() != IVertice.SENTIDO) {
					tmp.marcar();
					ArrayList<Vertice<T>> adr = getAdyacentes(tmp);
					if (adr != null && adr.size() > 0) {
						aux.addAll(adr);
					}
					mar.add(tmp);
					ret.add(tmp.getElemento());
				}
			}
		}
		for (IVertice<T> t : mar) {
			t.marcar();
		}
		return ret;
	}

	@Override
	public int treesInForest() {
		int ret = 0;
		int index = 0;
		ArrayList<T> all = new ArrayList<>();
		if (vertices != null) {
			T inicial = vertices.get(0).getElemento();
			while (all.size() != vertices.size()) {
				ArrayList<T> g = DFS(inicial);
				all.addAll(g);
				for (; index < vertices.size(); index++) {
					if (vertices.get(index).marcado() != IVertice.SENTIDO) {
						boolean ex = exist(all, vertices.get(index).getElemento());
						if (!vertices.get(index).getElemento().equals(inicial) && !ex) {
							inicial = vertices.get(index).getElemento();
							break;
						}

					}
				}
				ret++;
			}

		}
		return ret;

	}

	@Override
	public boolean existe(T ele) {
		boolean re = false;
		for (Vertice<T> tmp : vertices) {
			if (tmp.getElemento().equals(ele)) {
				re = true;
				break;
			}
		}
		return re;
	}

	//Busca el vertice en el arraylist y me retorna la posicion en el que lo encontró
	//De no encontrarlo retorna -1
	public int darIndice(T ele) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getElemento().equals(ele)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Arista<T> darArista(T inicio, T fin) {
		Arista<T> ret = null;
		int index = darIndice(inicio);
		int index1 = darIndice(fin);
		if (index < aristas.size()) {
			ArrayList<Arista<T>> ar = aristas.get(index);
			if (ar.size() > index1) {
				ret = ar.get(index1);
			}
		}
		return ret;
	}

	/**
	 * @return the vertices
	 */
	public ArrayList<Vertice<T>> getVertices() {
		return vertices;
	}

	/**
	 * @param vertices
	 *            the vertices to set
	 */
	public void setVertices(ArrayList<Vertice<T>> vertices) {
		this.vertices = vertices;
	}

	public RutasMatriz<T> dijsktra(Vertice<T> inicio) {
		if(vertices != null){
			if (inicio != null) {
				int tam = vertices.size();
				double[] pesos = new double[tam];
				@SuppressWarnings("unchecked")
				RutaMatriz<T>[] caminos = new RutaMatriz[tam];
				int index = 0;
				boolean mar[] = new boolean[tam];
				for (int i = 0; i < pesos.length; i++) {
					pesos[i] = Double.MAX_VALUE;
					caminos[i] = new RutaMatriz<>();
					if (vertices.get(i).getElemento().equals(inicio.getElemento())) {
						index = i;
					}
				}
				caminos[index].agregarVertice(inicio);
				pesos[index] = 0;
				int m = 0;
				while (m < tam) {
					int u = min(pesos, mar);
					mar[u] = true;
					for (int v = 0; v < tam; v++) {
						if (!mar[v]) {
							Arista<T> a = aristas.get(u).get(v);
							double pInI = a != null ? a.getPeso() : Double.MAX_VALUE;
							if (pesos[u] != Double.MAX_VALUE && pInI != Double.MAX_VALUE && pesos[u] + pInI < pesos[v]) {
								pesos[v] = pesos[u] + pInI;
								caminos[v].agregarTodos(caminos[u].getRuta());
								caminos[v].agregarVertice(vertices.get(v));
							}
						}
					}
					m++;
				}
				RutasMatriz<T> n = new RutasMatriz<>(caminos, pesos);
				return n;
			} else {
				return null;
			}
		}else
			return null;
	}

	private Vertice<T> get(T f) {
		Vertice<T> ret = null;
		for (int i = 0; vertices != null && i < vertices.size() && ret == null; i++) {
			if (vertices.get(i).getElemento().equals(f)) {
				ret = vertices.get(i);

			}
		}
		return ret;
	}

	private int min(double[] pesos, boolean[] mar) {
		int ret = 0;
		for (int i = 0; i < mar.length; i++) {
			if (!mar[i]) {
				ret = i;
				break;
			}
		}
		for (int j2 = ret + 1; j2 < pesos.length; j2++) {
			if (!mar[j2] && pesos[ret] > pesos[j2]) {
				ret = j2;
			}
		}
		return ret;

	}

	private ArrayList<Vertice<T>> getAdyacentes(Vertice<T> tmp) {
		ArrayList<Vertice<T>> ret = new ArrayList<>();
		int index = darIndice(tmp.getElemento());
		if (index != -1) {
			ArrayList<Arista<T>> ad = aristas.get(index);
			for (int i = 0; i < ad.size(); i++) {
				if (ad.get(i).getPeso() != Double.MAX_VALUE) {
					ret.add(vertices.get(i));
				}
			}
		}
		return ret;
	}

	private boolean exist(ArrayList<T> a, T e) {
		boolean re = false;
		for (T n : a) {
			if (n.equals(e)) {
				re = true;
				break;
			}
		}
		return re;
	}
}
