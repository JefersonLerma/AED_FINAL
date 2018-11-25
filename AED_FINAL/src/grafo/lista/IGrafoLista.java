package grafo.lista;

import java.util.*;
import grafo.*;

public interface IGrafoLista<T> extends IGrafo<T> {

	RutaLista<T> dijsktraParcial(T inicio, T fin);
	
	RutasLista<T> dijsktra(VerticeLista<T> inicio);

	HashMap<T, RutasLista<T>> floydWarshall();

	Arista<T> getArista(T inicio, T fin);
		
	VerticeLista<T> darVertice(T llave);

}
