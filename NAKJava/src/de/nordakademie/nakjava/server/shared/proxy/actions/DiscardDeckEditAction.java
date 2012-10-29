package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class DiscardDeckEditAction extends ActionContext {

	protected DiscardDeckEditAction(long batch, long sessionNr) {
		super(batch, sessionNr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
