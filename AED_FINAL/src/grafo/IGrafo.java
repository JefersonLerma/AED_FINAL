package grafo;

import java.util.ArrayList;

public interface IGrafo<T> {
	
	public final static int DIRIGIDO = 1;
	
	public final static int NO_DIRIGIDO = 2;
	
	IVertice<T> crearNodo(T vertice, T[] adyacentes, String[] nombres, int direccion );
	
	IVertice<T> crearNodo(T vertice, T[] adyacenter, String[] nombres, double[] pesos, int direccin);
	
	ArrayList<T> BFS(T vertice);
	
	ArrayList<T> DFS(T vertice);
	
	boolean existe(T elemento);
	
	int treesInForest();

}
