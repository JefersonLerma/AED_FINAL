package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import grafo.*;
import grafo.lista.*;
import junit.framework.TestCase;

public class TestGrafoLista extends TestCase implements ITestGrafo {

	private final static Integer[] ADYACENTES = {};

	private final static String[] DES = {};

	private GrafoLista<String> grafo;

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
	}
	
	private boolean existeRecorrido(ArrayList<String> esperado, String buscado){
		boolean existe = false;
		for (int i = 0; i < esperado.size() && !existe; i++) {
			if(esperado.get(i).equals(buscado))
				existe = true;
		}
		return existe;
	}

	private void setUp1() {
		grafo = new GrafoLista<>();
	}

	public void testAgregarVertices() throws IOException {
		setUp1();
		BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testGraphInput.txt")));
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
			assertEquals(true, grafo.existe(info[0]));
		}
	}

	public void testAgregarAristas() throws IOException {
		setUp1();
		fillGraph("./doc/dataTest/testGraphInput.txt");
		BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testGraphEdges")));
		String line = br.readLine();
		String[] info = line.split(",");
		while (line != null && !line.equals("")) {
			info = line.split(",");
			assertNotNull(grafo.getArista(info[0], info[1]));
			line = br.readLine();
		}
	}

	@Override
	public void testBFS() {
		// TODO Auto-generated method stub
		try {
			setUp1();
			fillGraph("./doc/dataTest/testGraphInput.txt");
			BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testLista/testGraphBFSOutput")));
			String recorridoCorrecto = br.readLine();
			int i = 0;
			while (i < 8) {
				char c = (char) (65 + i);
				ArrayList<String> way = grafo.BFS(c + "");
				String recorrido = way.toString();
				recorrido = recorrido.substring(1, recorrido.length() - 1);
				boolean correcto = recorrido.equals(recorridoCorrecto);
				assertEquals(true, correcto);
				recorridoCorrecto = br.readLine();
				i++;
			}
		} catch (Exception e) {
			fail("Error");
		}
	}

	@Override
	public void testDFS() {
		// TODO Auto-generated method stub
		int i = 0;
		int j = 0;
		try {
			setUp1();
			fillGraph("./doc/dataTest/testGraphInput.txt");
			BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testLista/testGraphDFSOutput")));
			String recorridoCorrecto = br.readLine();
			while (i < 8) {
				char c = (char) (65 + i);
				ArrayList<String> way = grafo.DFS(c + "");
				String recorrido = way.toString();
				recorrido = recorrido.substring(1, recorrido.length() - 1);
				boolean correcto = recorrido.equals(recorridoCorrecto);
				assertEquals(true, correcto);
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
		try{
			setUp1();
			fillGraph("./doc/dataTest/testGraphInput.txt");
			ArrayList<String> recorridosEsperados = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(new File("./doc/dataTest/testGraphDijsktraOutput")));
			String linea = br.readLine();
			while(linea != null && !linea.equals("")){
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
				char c = (char) (65+j);
				RutasLista<String> rutas = grafo.dijsktra(new VerticeLista<String>(c +""));
				while (i < rutas.getRutas().length) {
					RutaLista<String> ruta = rutas.getRutas()[i];
					boolean existe = existeRecorrido(recorridosEsperados, ruta.toString());
					System.out.println(ruta.toString());
					assertEquals(true, existe);
					i++;
				}
				i = 0;
				j++;
			}
		}catch(IOException e){
			fail();
		}
	}

	@Override
	public void testFloydWarshall(){
		// TODO Auto-generated method stub
	}

}
