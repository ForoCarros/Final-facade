package modelo.acceso;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class DAO<T> {
	/**
	 * Lee el archivo en la ruta dada.
	 * @param path ruta del archivo.
	 * @return lo que haya leído.
	 */
	public T leer(String path) {
		return leer(path, 0);
	}

	/**
	 * Lee el archivo en la ruta y posición especificada.<br>
	 * <p>Se comprueba que tanto el path como la posición sean válidos (no null y mayor que 0, respectivamente.<br>
	 * Se crea la variable <code>T t</code> para almacenar el retorno.<br>
	 * Crea el flujo de lectura <code>FileInputStream flujoR</code>.<br>
	 * Si <code>FileInputStream flujoR</code> no es null, se crea el <code>ObjectInputStream adaptador</code>.<br>
	 * Se recorre <code>adaptador.readObject()</code> hasta llegar a la posición deseada y la lectura se introduce en <code>T t</code>.<br>
	 * Se cierra el flujo <code>FileInputStream flujoR</code>.<br>
	 * Se retorna <code>T t</code>.
	 * </p>
	 * @param path ruta del archivo.
	 * @param posicion posición a leer.
	 * @return lo que haya leído.
	 */
	public T leer(String path, int posicion) {
		assert path != null && posicion >= 0;
		T t = null;
		FileInputStream flujoR = abrir(path);
		if (flujoR != null) {
			try {
				ObjectInputStream adaptador = new ObjectInputStream(flujoR);
				for (int i = 0; i <= posicion; i++) {
					t = (T) adaptador.readObject();
				}
			} catch (IOException | ClassNotFoundException e) {
				// e.printStackTrace();
				t = null;
			}
			cerrarFlujo(flujoR);
		}
		return t;
	}

	/**
	 * 
	 * @param path
	 * @param t
	 * @return
	 */
	public boolean grabar(String path, T t) {
		return grabar(path, t, false);
	}

	public boolean grabar(String path, T t, boolean adicion) {
		assert path != null && t != null;
		boolean retorno = true;
		File file = new File(path);
		boolean existe = file.exists() && adicion;
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream flujoW = abrir(path, adicion);
		try {
			ObjectOutputStream adaptador = null;
			if (existe) {
				adaptador = new MyObjectOutputStream(flujoW);
			} else {
				adaptador = new ObjectOutputStream(flujoW);
			}
			adaptador.writeObject(t);
		} catch (IOException e) {
			e.printStackTrace();
			retorno = false;
		}
		boolean cierreFalso = cerrarFlujo(flujoW);
		if (retorno)
			retorno = cierreFalso;
		return retorno;
	}

	private FileInputStream abrir(String path) {
		FileInputStream flujoR = null;
		File file = new File(path);
		try {
			if (file.exists()) {
				flujoR = new FileInputStream(path);
			}
		} catch (FileNotFoundException e) {
		}
		return flujoR;
	}

	private FileOutputStream abrir(String path, boolean adicion) {
		// no hay assert porque ya habria saltado en el public
		FileOutputStream flujoW = null;
		File file = new File(path);
		try {
			flujoW = new FileOutputStream(file, adicion);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return flujoW;

	}

	private boolean cerrarFlujo(Closeable closeable) {
		boolean retorno = true;
		try {
			closeable.close();
		} catch (IOException e) {
			retorno = false;
		}
		return retorno;
	}

	class MyObjectOutputStream extends ObjectOutputStream {

		public MyObjectOutputStream(OutputStream out) throws IOException {
			// quiero que se quede como esta
			super(out);
		}

		@Override
		protected void writeStreamHeader() throws IOException {
			// Este es el que escribe la cabecera
			/*
			 * La solucion pasa por eliminar la escritura de la cabecera para
			 * objetos de mi tipo
			 */
			// super.writeStreamHeader();
			// System.out.println("soy la otra");
		}
	}

	public boolean borrar(String rutaarchivo) {
		File file = new File(rutaarchivo);
		boolean retorno=false;
		if(file.exists())
			retorno=file.delete();
		return retorno;
	}

	public boolean borrarElemento(String pathDatos, Integer posicion) {
		int i = 0;
		boolean retorno = true;
		T t = leer(pathDatos, i);
		while (t != null) {
			if (i != posicion) {
				grabar("copia", t, true);
			}
			i++;
			t = leer(pathDatos, i);
		}
		File original = new File(pathDatos);
		File copia = new File("copia");
		if(!copia.exists()){
			try {
				copia.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!original.delete() || !copia.renameTo(original)) {
			retorno = false;
		}
		return retorno;
	}
}