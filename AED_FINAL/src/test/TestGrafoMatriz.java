package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import grafo.*;
import grafo.lista.RutaLista;
import grafo.lista.RutasLista;
import grafo.lista.VerticeLista;
import grafo.matriz.*;
import junit.framework.TestCase;

public class TestGrafoMatriz extends TestCase implements ITestGrafo {

	private GrafoMatriz<String> grafo;

	private void fillGraph(String data) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(data)));

		String line = br.readLine();
		String[] info = line.split(",");
		while (line != null && !line.equals("")) {
			info = line.split(",");
			if (info.length == 4) {
				String element = info[0];
				String[] ad = { info[1] };
				String[] rot = { info[2] };
				double[] pes = { Double.parseDouble(info[3]) };
				grafo.crearNodo(element, ad, rot, pes, IGrafo.NO_DIRIGIDO);

			}
			line = br.readLine();
		}
		br.close();
	}

	private boolean existeRecorrido(ArrayList<String> esperado, String buscado) {
		boolean existe = false;
		for (int i = 0; i < esperado.size() && !existe; i++) {
			if (esperado.get(i).equals(buscado))
				existe = true;
		}
		return existe;
	}

	private void setUp1() {

		grafo = new GrafoMatriz<>();
	}

	@Override
	public void testBFS() {
		// TODO Auto-generated method stub
		try {
			setUp1();
			System.out.println("BFS");
			fillGraph("./doc/dataTest/testGraphInput.txt");
			BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testMatriz/testGraphBFSOutput")));
			String recorridoCorrecto = br.readLine();
			int i = 0;
			while (i < 8) {
				char c = (char) (65 + i);
				ArrayList<String> way = grafo.BFS(c + "");
				String recorrido = way.toString();
				recorrido = recorrido.substring(1, recorrido.length() - 1);
				boolean correcto = recorrido.equals(recorridoCorrecto);
				 assertEquals(true, correcto);
				System.out.println(recorrido);
				recorridoCorrecto = br.readLine();
				i++;
			}
			br.close();
		} catch (Exception e) {
			fail("Error");
		}
	}

	@Override
	public void testDFS() {
		// TODO Auto-generated method stub
		try {
			System.out.println("DFS");
			setUp1();
			int i = 0;
			fillGraph("./doc/dataTest/testGraphInput.txt");
			BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testMatriz/testGraphDFSOutput")));
			String recorridoCorrecto = br.readLine();
			while (i < 8) {
				char c = (char) (65 + i);
				ArrayList<String> way = grafo.DFS(c + "");
				String recorrido = way.toString();
				recorrido = recorrido.substring(1, recorrido.length() - 1);
				boolean correcto = recorrido.equals(recorridoCorrecto);
				 assertEquals(true, correcto);
				System.out.println(recorrido);
				recorridoCorrecto = br.readLine();
				i++;
			}
			br.close();
		} catch (Exception e) {
			fail("Error");
		}
	}

	@Override
	public void testDijsktra() {
		// TODO Auto-generated method stub
		try {
			setUp1();
			fillGraph("./doc/dataTest/testGraphInput.txt");
			System.out.println("DIJSKTRA");
			ArrayList<String> recorridosEsperados = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testGraphDijsktraOutput")));
			String linea = br.readLine();
			while (linea != null && !linea.equals("")) {
				String[] recorridos = linea.split("\t");
				for (int i = 0; i < recorridos.length; i++) {
					recorridosEsperados.add(recorridos[i]);
				}
				linea = br.readLine();
			}
			br.close();

			int i = 0;
			int j = 0;
			while (j < grafo.getVertices().size()) {
				char c = (char) (65 + j);

				RutasMatriz<String> rutas = grafo.dijsktra(new Vertice<String>(c + ""));
				while (i < rutas.getRutas().length) {
					RutaMatriz<String> ruta = rutas.getRutas()[i];
					boolean existe = existeRecorrido(recorridosEsperados, ruta.toString());
					assertEquals(true, existe);
					i++;
				}
				i = 0;
				j++;
			}
		} catch (IOException e) {
			fail();
		}
	}

	@Override
	public void testFloydWarshall() {
		// TODO Auto-generated method stub
		try {
			setUp1();
			fillGraph("./doc/dataTest/testGraphInput.txt");
			ArrayList<String> recorridosEsperados = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testGraphDijsktraOutput")));
			String linea = br.readLine();
			while (linea != null && !linea.equals("")) {
				String[] recorridos = linea.split("\t");
				for (int i = 0; i < recorridos.length; i++) {
					recorridosEsperados.add(recorridos[i]);
				}
				linea = br.readLine();
			}
			br.close();

			int i = 0;
			int j = 0;
			RutaMatriz<String>[][] rutas = grafo.floydWarshall();
			while (i < grafo.getVertices().size()) {
				j = 0;
				while (j < rutas.length) {
					RutaMatriz<String> ruta = rutas[j][i];
					String recorrido = ruta.toString();
					boolean existe = existeRecorrido(recorridosEsperados,recorrido);
					assertEquals(true, existe);
					j++;
				}
				j = 0;
				i++;
			}
		} catch (IOException e) {
			fail();
		}
	}

}
