package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class TypeDeckNameAction extends KeyAction {

	public TypeDeckNameAction(int key, long batch, long sessionNr)
			throws RemoteException {
		super(key, batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
