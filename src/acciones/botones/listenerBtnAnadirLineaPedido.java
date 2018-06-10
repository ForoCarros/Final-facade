package acciones.botones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import control.ParaUI;
import logica.Logica;
import modelo.data.Articulo;
import modelo.data.Cliente;
import modelo.data.Linea;
import modelo.data.Pedido;
import utiles.Rutas;
import vista.AltaPedido;

public class listenerBtnAnadirLineaPedido implements ActionListener {
	private ParaUI paraUI;

	public listenerBtnAnadirLineaPedido(ParaUI paraUI) {
		super();
		this.paraUI = paraUI;
		//
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AltaPedido panel = this.paraUI.getPanelAltaPedido();
		assert panel.getComboClientes() != null : "Cliente nulo";
		assert panel.getComboArticulos() != null : "Art�culo nulo";
		assert !panel.getTxtCantidad().getText().isEmpty() : "Cantidad igual o inferior a 0";
		if (this.paraUI.getPedidoTemporal() == null) {
			String clave = panel.getComboArticulos().getSelectedItem().toString();
			Cliente cliente = this.paraUI.getLogica().obtenerCliente(clave);
		}
		Pedido pedido = this.paraUI.getPedidoTemporal();
		String clave = panel.getComboArticulos().getSelectedItem().toString();
		Articulo articulo = (Articulo) this.paraUI.getLogica().obtenerArticulo(clave);
		System.out.println(articulo);
		Linea linea = new Linea(articulo, Integer.valueOf(panel.getTxtCantidad().getText()));
		pedido.insertarLinea(linea);
		this.paraUI.setPedidoTemporal(pedido);
		((DefaultTableModel) panel.getLineasPedido().getModel()).addRow(toObjectArray(linea));
		this.paraUI.actualizarPrecioVentanaPedido(
				Float.valueOf(this.paraUI.getPanelAltaPedido().getTxtPrecioFinal().getText())
						+ articulo.getCurrentPrice());
	}

	public Object[] toObjectArray(Linea obj) {
		String nombre = obj.getArticulo().getNombre();
		int cantidad = obj.getCantidad();
		float precio = obj.getArticulo().getCurrentPrice();
		float total = cantidad * precio;
		Object[] object = { nombre, cantidad, precio, total };
		return object;
	}
}