package grafo.lista;

import java.util.*;
import grafo.*;

public interface IVerticeLista<T> extends IVertice<T>{

	void agregarArista(VerticeLista<T> ad, double p, String s);
	
	void agregarArista(VerticeLista<T> nuevo, String nombre);
	
	ArrayList<VerticeLista<T>> darAdyacentes();
	
	String darNombreArista(VerticeLista<T> fin);
	
	void agregarTodos(ArrayList<Arista<T>> nuevas);
	
	double darPesoArista(VerticeLista<T> vertice);
	
}
