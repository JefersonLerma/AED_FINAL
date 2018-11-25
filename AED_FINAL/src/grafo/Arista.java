package grafo;

public class Arista<T>{
	
	private double peso;
	
	private String nombre;
	
	private Vertice<T> relacion;
	
	public Arista(double peso, String nombre) {
		super();
		this.peso = peso;
		this.nombre = nombre;
	}

	public Arista(String nombre, Vertice<T> relacion) {
		super();
		this.nombre = nombre;
		this.relacion = relacion;
	}

	public Arista(double peso, String nombre, Vertice<T> relacion) {
		super();
		this.peso = peso;
		this.nombre = nombre;
		this.relacion = relacion;
	}

	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}



	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}



	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}



	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	/**
	 * @return the relacion
	 */
	public Vertice<T> getRelacion() {
		return relacion;
	}



	/**
	 * @param relacion the relacion to set
	 */
	public void setRelacion(Vertice<T> relacion) {
		this.relacion = relacion;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return relacion.getElemento().toString();
	}
	

}
