package acciones.botones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ParaUI;
import modelo.data.Articulo;
import vista.ConsultaArticulo;

public class listenerBtnConsultarArticulo implements ActionListener {
	private ParaUI paraUI;

	public listenerBtnConsultarArticulo(ParaUI paraUI) {
		super();
		this.paraUI = paraUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ConsultaArticulo panel = this.paraUI.getPanelConsultarArticulo();
		String clave = panel.getComboArticulos().getSelectedItem().toString();
		Articulo articulo = this.paraUI.getLogica().obtenerArticulo(clave);
		panel.getTxtDescripcion().setText(articulo.getDescripcion());
		panel.getTxtPrecioActual().setText(String.valueOf(articulo.getCurrentPrice()));
	}

}
