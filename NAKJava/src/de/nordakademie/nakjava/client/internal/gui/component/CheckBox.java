package de.nordakademie.nakjava.client.internal.gui.component;

import java.rmi.RemoteException;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class CheckBox extends JCheckBox implements ActionContextHolder {

	private ActionContextSelector selector;
	private ActionContext context;

	public CheckBox(ActionContextSelector selector) {
		this.selector = selector;
		this.setEnabled(false);

		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				try {
					context.perform();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		this.context = context;
		this.setEnabled(true);

	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		return selector.select(context);
	}

	@Override
	public void noActionContextAvailable() {
		this.setEnabled(false);
	}

	@Override
	public void setActionContextSelector(ActionContextSelector selector) {
		this.selector = selector;
	}

	@Override
	public void revokeActionContext(long batch) {
		this.selector = null;
		this.setEnabled(false);
	}

}
