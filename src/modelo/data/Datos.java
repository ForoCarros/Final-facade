package modelo.data;

import java.util.Map;
import java.util.TreeMap;

import modelo.acceso.AlmacenIndice;
import modelo.acceso.AlmacenIndividual;
import modelo.acceso.AlmacenIndividualSet;
import modelo.acceso.AlmacenRutaDestino;
import modelo.acceso.AlmacenRutaMapeada;

public class Datos {
	private String pathIndiceCliente = "./data/clientes/clientes.index";
	private String pathDatosCliente = "./data/clientes/clientes.data";
	private String pathMapaArticulos = "articulos";
	private String pathArchivosMapaArticulos = "./data/articulos/";
	private String pathPedidos = "./data/pedidos/";
	private String pathNumeroPedido = "./data/pedidos/numPedido.data";

	private AlmacenIndice<Cliente, String> almacenClientes;
	private AlmacenRutaMapeada<Articulo, String> almacenArticulos;
	private AlmacenRutaDestino<Pedido> almacenPedidos;
	private AlmacenIndividual<Integer> numeroPedido;

	public Datos() {
		this.almacenClientes = new AlmacenIndice<Cliente, String>(pathIndiceCliente, pathDatosCliente);
		this.almacenArticulos = new AlmacenRutaMapeada<>("art", pathArchivosMapaArticulos, pathMapaArticulos);
		this.almacenPedidos = new AlmacenRutaDestino<>(pathPedidos, "ped");
		numeroPedido = new AlmacenIndividual<>();
		Integer obtener = numeroPedido.obtener(pathNumeroPedido);
		if (obtener == null) {
			numeroPedido.grabar(pathNumeroPedido, 0);
		}
	}
	
	public boolean grabar(Cliente cliente) {
		return this.almacenClientes.grabar(cliente, cliente.getKey());
	}

	public boolean grabar(Articulo articulo) {
		return this.almacenArticulos.grabar(articulo, articulo.getKey(), articulo.getIdArticulo());
	}

	public boolean grabar(Pedido pedido) {
		return this.almacenPedidos.grabar(pedido.getCliente().getKey(), String.valueOf(pedido.getNumero()), pedido);
	}

	public boolean grabar(Integer integer) {
		return numeroPedido.grabar(pathNumeroPedido, integer);
	}

	/// Igual con el resto de metodos de obtener. Pondre uno de prueba
	public Cliente obtenerCliente(String clave) {
		return this.almacenClientes.obtener(clave);
	}

	public void obtenerAlgo() {

	}

	public Articulo obtenerArticulo(String clave) {
		return this.almacenArticulos.obtener(clave);
	}

	public Pedido obtenerPedido(Pedido pedido, String dniCliente, int numeroPedido) {
		return this.almacenPedidos.obtener(null, null);
	}

	public boolean borrar(String clave) {
		return this.almacenClientes.borrar(clave);
	}

	public Articulo consularArticulo(String clave) {
		return this.almacenArticulos.obtener(clave);
	}

	public Map obtenerMapaArticulos() {
		return this.almacenArticulos.getMapa();
	}

	public TreeMap obtenerIndiceClientes() {
		return this.almacenClientes.getIndice();
	}
}
