package logica;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import modelo.acceso.AlmacenIndice;
import modelo.acceso.AlmacenIndividual;
import modelo.acceso.AlmacenIndividualSet;
import modelo.acceso.AlmacenMap;
import modelo.acceso.AlmacenRutaDestino;
import modelo.acceso.AlmacenRutaMapeada;
import modelo.data.Articulo;
import modelo.data.Cliente;
import modelo.data.Pedido;
import modelo.data.Proveedor;

//La suite de test tiene que daos en verde. Luego podeis probar esta clase
public class Logica {
	// Vamos a expresar los diferentes almacenes
	private String pathIndiceCliente = "./data/clientes/clientes.index";
	private String pathDatosCliente = "./data/clientes/clientes.data";
	private String pathDatosProveedores = "./data/proveedores/proveedores.data";
	private String pathMapaArticulos = "articulos";
	private String pathArchivosMapaArticulos = "./data/articulos/";
	private String pathPedidos = "./data/pedidos/";
	private String pathNumeroPedido = "./data/pedidos/numPedido.data";

	// Cliente
	private AlmacenIndice<Cliente, String> clientes;
	// Proveedores
	private AlmacenIndividualSet<Proveedor> proveedores;
	// Articulo
	private AlmacenRutaMapeada<Articulo, String> articulos;
	// Pedidos
	private AlmacenRutaDestino<Pedido> pedidos;
	// numeroUltimoPedido
	private AlmacenIndividual<Integer> numeroPedido;

	public Logica() {

		clientes = new AlmacenIndice<Cliente, String>(pathIndiceCliente, pathDatosCliente);
		// proveedores = new AlmacenIndividualSet<>(new TreeSet<Proveedor>(),
		// pathDatosProveedores);
		articulos = new AlmacenRutaMapeada<>("art", pathArchivosMapaArticulos, pathMapaArticulos);
		pedidos = new AlmacenRutaDestino<>(pathPedidos, "ped");
		numeroPedido = new AlmacenIndividual<>();
		Integer obtener = numeroPedido.obtener(pathNumeroPedido);
		if (obtener == null) {
			numeroPedido.grabar(pathNumeroPedido, 0);
		}
	}

	public boolean grabar(Cliente cliente) {
		return clientes.grabar(cliente, cliente.getKey());
	}

	public boolean grabar(Proveedor proveedor) {
		return proveedores.grabar(proveedor);
	}

	public boolean grabar(Articulo articulo) {
		return articulos.grabar(articulo, articulo.getKey(), articulo.getIdArticulo());
	}

	public boolean grabar(Pedido pedido) {
		return pedidos.grabar(pedido.getCliente().getKey(), String.valueOf(pedido.getNumero()), pedido);
	}

	public boolean grabar(Integer integer) {
		return numeroPedido.grabar(pathNumeroPedido, integer);
	}

	/// Igual con el resto de metodos de obtener. Pondre uno de prueba
	public Cliente obtenerCliente(String clave) {
		return this.clientes.obtener(clave);
	}

	public void obtenerPedido() {

	}

	public Articulo obtenerArticulo(String clave) {
		return this.articulos.obtener(clave);
	}

	public void obtener(Pedido pedido, String dniCliente, int numeroPedido) {

	}

	public boolean borrarCliente(String clave) {
		return this.clientes.borrar(clave);
	}

	public Articulo consularArticulo(String clave) {
		return this.articulos.obtener(clave);
	}

	public Map obtenerMapaArticulos() {
		return this.articulos.getMapa();
	}

	public TreeMap obtenerIndiceClientes() {
		return this.clientes.getIndice();
	}
}
