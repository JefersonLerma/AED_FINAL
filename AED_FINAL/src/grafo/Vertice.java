package grafo;

public class Vertice<T> implements IVertice<T> {

	private boolean marca;

	private T elemento;

	private IVertice<T> relacion;

	public Vertice(T elemento) {
		super();
		this.elemento = elemento;
	}

	/**
	 * @return the marca
	 */
	public boolean isMarca() {
		return marca;
	}

	/**
	 * @param marca
	 *            the marca to set
	 */
	public void setMarca(boolean marca) {
		this.marca = marca;
	}

	/**
	 * @return the elemento
	 */
	public T getElemento() {
		return elemento;
	}

	/**
	 * @param elemento
	 *            the elemento to set
	 */
	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	/**
	 * @return the relacion
	 */
	public IVertice<T> getRelacion() {
		return relacion;
	}

	/**
	 * @param relacion
	 *            the relacion to set
	 */
	public void setRelacion(IVertice<T> relacion) {
		this.relacion = relacion;
	}

	@Override
	public boolean marcado() {
		// TODO Auto-generated method stub
		return marca;
	}

	@Override
	public void marcar() {
		// TODO Auto-generated method stub
		marca = !marca;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return elemento.toString();
	}

}
