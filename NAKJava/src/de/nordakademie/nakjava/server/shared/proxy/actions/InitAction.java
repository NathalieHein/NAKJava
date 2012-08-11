package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class InitAction extends ActionContext {

	public InitAction(long batch) throws RemoteException {
		super(batch);
	}

	@Override
	protected ServerAction getAction() throws RemoteException {
		return new ActionAbstractImpl() {

			@Override
			protected void performImpl(Model model) {
				// TODO Kommentar fehlt

			}
		};
	}

}
