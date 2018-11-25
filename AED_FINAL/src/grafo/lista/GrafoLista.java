package grafo.lista;

import java.util.*;
import grafo.*;


public class GrafoLista<T> implements IGrafoLista<T> {

	private Hashtable<T, VerticeLista<T>> vertices;

	public VerticeLista<T> crearNodo(T element, T[] adyacents, String[] names, double[] weights, int address) {
		if (adyacents != null) {
			VerticeLista<T> nueva = darVertice(element) == null ? new VerticeLista<>(element)
					: (VerticeLista<T>) darVertice(element);
			ArrayList<Arista<T>> aristas = new ArrayList<>();

			for (int i = 0; i < adyacents.length; i++) {
				VerticeLista<T> nuevoNodo = (VerticeLista<T>) (darVertice(adyacents[i]) == null
						? new VerticeLista<>(adyacents[i]) : darVertice(adyacents[i]));
				Arista<T> nuevaArista = new Arista<>(weights[i], names[i], nuevoNodo);
				if (address == NO_DIRIGIDO) {
					nuevoNodo.agregarArista(nueva, weights[i], names[i]);
					if (vertices == null)
						vertices = new Hashtable<>();
					if (!existe(nuevoNodo.getElemento()))
						addVertice(nuevoNodo);
				}
				aristas.add(nuevaArista);

			}

			nueva.agregarTodos(aristas);
			if (vertices == null)
				vertices = new Hashtable<>();
			if (!existe(nueva.getElemento()))
				addVertice(nueva);
			return nueva;
		} else {

			VerticeLista<T> no = new VerticeLista<>(element);
			if (vertices == null) {
				vertices = new Hashtable<>();
			}
			if (!existe(element)) {
				addVertice(no);

			}
			return no;

		}
	}

	private void addVertice(VerticeLista<T> no) {
		if (vertices == null) {
			vertices = new Hashtable<>();
		}
		vertices.put(no.getElemento(), no);
	}

	public VerticeLista<T> crearNodo(T element, T[] adyacents, String[] names, int address) {
		if (adyacents != null) {
			VerticeLista<T> nueva = darVertice(element) == null ? new VerticeLista<>(element)
					: (VerticeLista<T>) darVertice(element);
			ArrayList<Arista<T>> aristas = new ArrayList<>();
			for (int i = 0; i < adyacents.length; i++) {
				VerticeLista<T> no = (VerticeLista<T>) (darVertice(adyacents[i]) == null
						? new VerticeLista<>(adyacents[i]) : darVertice(adyacents[i]));
				Arista<T> nuevaA = new Arista<>(names[i], no);
				if (address == NO_DIRIGIDO) {
					no.agregarArista(nueva, names[i]);
					if (vertices == null) {
						vertices = new Hashtable<>();
					}
					if (!existe(no.getElemento())) {
						addVertice(no);

					}
				}
				aristas.add(nuevaA);
			}
			nueva.agregarTodos(aristas);
			if (vertices == null) {
				vertices = new Hashtable<>();
			}
			if (!existe(nueva.getElemento())) {
				addVertice(nueva);

			}
			return nueva;
		} else {
			VerticeLista<T> no = new VerticeLista<>(element);
			if (vertices == null) {
				vertices = new Hashtable<>();
			}
			if (!existe(element)) {
				addVertice(no);

			}

			return no;
		}
	}

	public ArrayList<T> BFS(T el) {
		VerticeLista<T> ele = darVertice(el);
		ArrayList<T> ret = new ArrayList<>();
		ArrayList<VerticeLista<T>> mar = new ArrayList<>();

		ArrayDeque<VerticeLista<T>> aux = new ArrayDeque<>();
		if (ele != null) {
			aux.add(ele);
			while (!aux.isEmpty()) {
				VerticeLista<T> tmp = aux.remove();
				if (tmp.marcado() != VerticeLista.SENTIDO) {
					tmp.marcar();
					Collection<VerticeLista<T>> adr = tmp.darAdyacentes();
					if (adr != null && adr.size() > 0) {
						aux.addAll(adr);
					}
					mar.add(tmp);
					ret.add(tmp.getElemento());
				}
			}
		}
		for (VerticeLista<T> a : mar) {
			a.marcar();
		}
		return ret;
	}

	public ArrayList<T> DFS(T el) {
		VerticeLista<T> ele = darVertice(el);
		ArrayList<VerticeLista<T>> mar = new ArrayList<>();
		ArrayList<T> ret = new ArrayList<>();
		Stack<VerticeLista<T>> aux = new Stack<>();
		if (ele != null) {
			aux.add(ele);
			while (!aux.isEmpty()) {
				VerticeLista<T> tmp = aux.pop();
				if (tmp.marcado() != IVerticeLista.SENTIDO) {
					tmp.marcar();
					ArrayList<VerticeLista<T>> adr = tmp.darAdyacentes();
					if (adr != null && adr.size() > 0) {
						aux.addAll(adr);

					}
					mar.add(tmp);
					ret.add(tmp.getElemento());
				}

			}

		}
		for (VerticeLista<T> a : mar) {
			a.marcar();
		}
		return ret;
	}

	public RutaLista<T> dijsktraParcial(T ini, T f) {

		VerticeLista<T> fin = darVertice(f);
		ArrayList<VerticeLista<T>> vertices = getVertices();

		if (fin != null) {
			int index = -1;
			for (int i = 0; i < vertices.size(); i++) {
				if (vertices.get(i).getElemento().equals(fin.getElemento())) {
					index = i;
					break;
				}
			}
			if (index >= 0) {
				VerticeLista<T> inicio = darVertice(ini);
				RutasLista<T> ca = dijsktra(inicio);

				return ca.getRutas()[index];
			} else {
				return null;
			}

		} else {
			return null;
		}

	}

	@Override
	public RutasLista<T> dijsktra(VerticeLista<T> inicio) {
		ArrayList<VerticeLista<T>> vertices = getVertices();

		if (vertices != null) {
			if (inicio != null) {
				int tam = vertices.size();
				double[] pesos = new double[tam];
				@SuppressWarnings("unchecked")
				RutaLista<T>[] caminos = new RutaLista[tam];
				int index = 0;
				boolean mar[] = new boolean[tam];
				for (int i = 0; i < pesos.length; i++) {
					pesos[i] = Double.MAX_VALUE;
					caminos[i] = new RutaLista<T>();
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
							Arista<T> ret = getArista(vertices.get(u).getElemento(), vertices.get(v).getElemento());
							double pInI = ret != null ? ret.getPeso() : Double.MAX_VALUE;
							if (pesos[u] != Double.MAX_VALUE && pInI != Double.MAX_VALUE
									&& pesos[u] + pInI < pesos[v]) {
								pesos[v] = pesos[u] + pInI;
								caminos[v].agregarTodos(caminos[u].getRuta());
								caminos[v].agregarVertice(vertices.get(v));
							}
						}
					}
					m++;
				}
				RutasLista<T> n = new RutasLista<>(caminos, pesos);

				return n;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public HashMap<T, RutasLista<T>> floydWarshall() {
		HashMap<T, RutasLista<T>> ret = new HashMap<>();
		ArrayList<VerticeLista<T>> vertices = getVertices();

		for (int i = 0; i < vertices.size(); i++) {
			RutasLista<T> cami = dijsktra(vertices.get(i));
			ret.put(vertices.get(i).getElemento(), cami);
		}
		return ret;
	}

	@Override
	public int treesInForest() {
		int ret = 0;
		int index = 0;
		ArrayList<T> all = new ArrayList<>();
		ArrayList<VerticeLista<T>> vertices = getVertices();
		if (vertices != null) {
			T inicial = vertices.get(0).getElemento();
			while (all.size() != vertices.size()) {
				ArrayList<T> g = DFS(inicial);
				all.addAll(g);
				for (; index < vertices.size(); index++) {
					if (vertices.get(index).marcado() != IVerticeLista.SENTIDO) {
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
	public boolean existe(T elemento) {
		boolean re = false;
		if (vertices != null) {
			re = vertices.containsKey(elemento);
		}
		return re;
	}

	@Override
	public VerticeLista<T> darVertice(T llave) {
		VerticeLista<T> ret = null;
		if (vertices != null) {
			ret = vertices.get(llave);
		}
		return ret;
	}

	@Override
	public Arista<T> getArista(T inicio, T fin) {
		Arista<T> ret = null;
		VerticeLista<T> i = darVertice(inicio);
		if (i != null) {
			ArrayList<Arista<T>> ad = i.getAristas();
			for (int j = 0; j < ad.size(); j++) {
				if (ad.get(j).getRelacion().getElemento().equals(fin)) {
					if (ret != null) {
						if (ret.getPeso() > ad.get(j).getPeso()) {
							ret = ad.get(j);
						}
					} else {
						ret = ad.get(j);
					}
				}
			}
		}
		return ret;
	}

	/**
	 * @return the vertices
	 */
	public ArrayList<VerticeLista<T>> getVertices() {
		ArrayList<VerticeLista<T>> array = new ArrayList<>();
		if (vertices != null) {

			array = new ArrayList<>(vertices.values());
		}
		return array;
	}

	/**
	 * @param vertices
	 *            the vertices to set
	 */
	public void setVertices(ArrayList<VerticeLista<T>> vertices) {

		if (this.vertices == null) {
			this.vertices = new Hashtable<T, VerticeLista<T>>();
		}
		for (VerticeLista<T> vertice : vertices) {
			this.vertices.put(vertice.getElemento(), vertice);
		}
	}

	private int min(double[] a, boolean[] ar) {
		int ret = 0;
		for (int i = 0; i < ar.length; i++) {
			if (!ar[i]) {
				ret = i;
				break;
			}
		}
		for (int j2 = ret + 1; j2 < a.length; j2++) {
			if (!ar[j2] && a[ret] > a[j2]) {
				ret = j2;
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

