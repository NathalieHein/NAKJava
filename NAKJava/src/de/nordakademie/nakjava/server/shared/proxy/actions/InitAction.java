package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.shared.proxy.Action;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class InitAction extends ActionContext {

	public InitAction() throws RemoteException {
		super();
	}

	@Override
	protected Action getAction() throws RemoteException {
		return new ActionAbstractImpl() {

			@Override
			protected void performImpl(Model model) {
				// TODO Kommentar fehlt

			}
		};
	}

}
