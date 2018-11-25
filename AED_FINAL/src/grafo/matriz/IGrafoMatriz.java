package grafo.matriz;

import java.util.*;
import grafo.*;

public interface IGrafoMatriz<T> extends IGrafo<T>{

	RutaMatriz<T> dijsktraParcial(T inicio, T fin);
	
	RutaMatriz<T>[][] floydWarshall();

	int darIndice(T elemento);

	Arista<T> darArista(T inicio, T fin);

}
