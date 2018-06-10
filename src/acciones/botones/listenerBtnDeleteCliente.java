package acciones.botones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ParaUI;
import vista.BajaCliente;

public class listenerBtnDeleteCliente implements ActionListener {
	private ParaUI paraUI;

	public listenerBtnDeleteCliente(ParaUI paraUI) {
		super();
		this.paraUI = paraUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BajaCliente ventana = this.paraUI.getPanelBajaCliente();
		//System.out.println(ventana.getComboClientes().getSelectedItem().toString());
		if (this.paraUI.getLogica().borrarCliente(ventana.getComboClientes().getSelectedItem().toString())) {
			ventana.getTxtMensajeBorrar().setText("cliente borrado");
		} else {
			ventana.getTxtMensajeBorrar().setText("ERROR");
		}
	}

}
