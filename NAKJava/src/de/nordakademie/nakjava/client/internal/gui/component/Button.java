package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class Button extends JButton implements ActionListener {

	private ActionContext actionContext;

	private boolean initialized = false;

	public ActionContext getActionContext() {
		return actionContext;
	}

	public void setActionContext(ActionContext actionContext) {
		this.actionContext = actionContext;
		if (!initialized) {
			this.addActionListener(this);
			initialized = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (actionContext != null) {
			try {
				actionContext.perform();
				actionContext = null;
				this.setEnabled(false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
