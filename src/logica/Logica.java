package logica;

import java.util.Map;
import java.util.TreeMap;
import modelo.data.Articulo;
import modelo.data.Cliente;
import modelo.data.Datos;
import modelo.data.Pedido;

//La suite de test tiene que daos en verde. Luego podeis probar esta clase
public class Logica {
	private Datos datos;

	public Logica() {
		this.datos = new Datos();
	}

	public boolean grabar(Cliente cliente) {
		return this.datos.grabar(cliente);
	}

	public boolean grabar(Articulo articulo) {
		return this.datos.grabar(articulo);
	}

	public boolean grabar(Pedido pedido) {
		return this.datos.grabar(pedido);
	}

	public boolean grabar(Integer integer) {
		return this.datos.grabar(integer);
	}

	/// Igual con el resto de metodos de obtener. Pondre uno de prueba
	public Cliente obtenerCliente(String clave) {
		// return this.datos.obtener(clave);
		return null;
	}

	public void obtenerPedido() {

	}

	public Articulo obtenerArticulo(String clave) {
		return this.datos.obtenerArticulo(clave);
	}

	public void obtener(Pedido pedido, String dniCliente, int numeroPedido) {

	}

	public boolean borrarCliente(String clave) {
		return this.datos.borrar(clave);
	}

	public Map obtenerMapaArticulos() {
		return this.datos.obtenerMapaArticulos();
	}

	public TreeMap obtenerIndiceClientes() {
		return this.datos.obtenerIndiceClientes();
	}
}
