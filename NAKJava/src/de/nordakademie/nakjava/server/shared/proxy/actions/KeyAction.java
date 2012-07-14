package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.shared.proxy.Action;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class KeyAction extends ActionContext {

	private int key;

	public KeyAction(int key) throws RemoteException {
		super();
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	@Override
	protected Action getAction() throws RemoteException {
		return new ActionAbstractImpl() {

			@Override
			protected void performImpl(Model model) {
				model.setName(model.getName() + KeyEvent.getKeyText(key));

			}
		};
	}

}
