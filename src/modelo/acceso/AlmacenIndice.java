package modelo.acceso;

import java.util.TreeMap;

import modelo.acceso.DAO;
import modelo.data.Indexable;

public class AlmacenIndice<T, K> {
	private String pathIndice;
	private String pathDatos;
	private TreeMap<K, Integer> indice;
	DAO<Object> dao;

	public AlmacenIndice(String pathIndice, String pathDatos) {
		super();
		this.pathIndice = pathIndice;
		this.pathDatos = pathDatos;
		assert validate();
		this.indice = new TreeMap<>();
		dao = new DAO<>();
	}

	/**
	 * Valida la ruta de datos y de indice.
	 * 
	 * @return true si es correcto, false si no.
	 */
	private boolean validate() {
		return this.pathIndice != null && this.pathDatos != null;
	}

	/**
	 * Borra un objeto con la clave <code>K k</code>.<br>
	 * Lee el índice y lo almacena en la variable global
	 * <code>TreeMap<K, Integer> indice</code>.<br>
	 * Crea la variable <code>boolean retorno</code> y la inicializaen false.<br>
	 * Se comprueba que el índice contenga la clave <code>K k</code>. Si la
	 * contiene:<br>
	 * Se crea la variable <code>Integer posicion</code> y se inicializa con la
	 * posición de la clave borrada.<br>
	 * Se comprueba que <code>Integer posicion</code> no sea nulo. Si se cumple:<br>
	 * <code>boolean retorno</code> será true si se ha borrado el elemento y false
	 * si no.<br>
	 * Si <code>bolean retorno</code> es true, se lee el índice. Si es false, se
	 * reestablece el índice y se guarda.
	 * 
	 * @param k
	 *            la clave.
	 * @return true si se ha borrado el elemento, false si no.
	 */
	public boolean borrar(K k) {
		leerIndice();
		boolean retorno = false;
		if (indice.containsKey(k)) {
			Integer posicion = indice.remove(k);
			if (posicion != null) {
				retorno = dao.borrarElemento(pathDatos, posicion);
				if (!retorno) {
					leerIndice();
				} else {
					recargaIndice();
					dao.grabar(pathIndice, indice);
				}

			}
		}
		return retorno;
	}

	/**
	 * Si el elemento no ha podido ser borrado, se vuelve a introducir su clave en
	 * el índice, en la misma posición en la que estaba. Se crea un nuevo índice
	 * <code>TreeMap indice</code>.<br>
	 * Se crea la posición <code>int posicion</code> donde se almacena la
	 * posición.<br>
	 * Se crea <code>T t</code> donde se almacenará el elemento en la posición dada
	 * de datos.<br>
	 * Mientras que <code>T t</code> no sea null:<br>
	 * Se crea el elemento <code>Indexable elemento</code>.<br>
	 * Se crea <code>K k</code> donde se almacenará la clave del elemento.<br>
	 * Se introduce la clave en el índice en la posición correspondiente.<br>
	 * Se aumenta la posición y se vuelve a leer los datos en la posición
	 * correspondiente y se almacena en <code>T t</code>.
	 */
	private void recargaIndice() {
		indice = new TreeMap<>();
		int posicion = 0;
		T t = (T) dao.leer(pathDatos, posicion);
		while (t != null) {
			Indexable<K> elemento = (Indexable<K>) t;
			K k = elemento.getKey();
			indice.put(k, posicion);
			posicion++;
			t = (T) dao.leer(pathDatos, posicion);
		}

	}

	/**
	 * Lee el índice en la ruta.
	 */
	private void leerIndice() {
		indice = (TreeMap<K, Integer>) dao.leer(pathIndice);
	}

	/**
	 * Obtiene un elemento por la clave k.
	 * 
	 * @param k
	 *            la clave a buscar.
	 * @return el elemento que coincide con la clave.
	 */
	public T obtener(K k) {
		leerIndice();
		if (indice == null) {
			indice = new TreeMap<>();
			dao.grabar(pathIndice, indice);
		}
		T retorno = null;
		Integer posicion = indice.get(k);
		if (posicion != null) {
			retorno = (T) dao.leer(pathDatos, posicion);
		}
		return retorno;
	}

	/**
	 * Almacen el elemnto de clase T con Clave K, hay que pasarla
	 * 
	 * @param t
	 *            el objeto a grabar
	 * @param k
	 *            la propiedad clave o indice del objeto t
	 * @return true si ha almacenado y false en caso contrario
	 */
	public boolean grabar(T t, K k) {
		boolean retorno = false;
		// miro el ultimo indice. siempre hay un mapa aqui
		Integer value = indice.size();
		// si es el primer elemento lastentry sera null
		// si al meterlo el valor es null es que NO esta repetido
		if (indice.put(k, value) == null) {
			// si se almacena bien en el archivo de datos
			if (dao.grabar(pathDatos, t, true)) {
				retorno = true;
				dao.grabar(pathIndice, indice);
			} else {
				leerIndice();
			}
		}
		return retorno;
	}

	public TreeMap getIndice() {
		leerIndice();
		return this.indice;
	}
}
