package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public abstract class KeyAction extends ActionContext {

	private int key;

	public KeyAction(int key, long batch, long sessionNr)
			throws RemoteException {
		super(batch, sessionNr);
		this.key = key;
	}

	public int getKey() {
		return key;
	}

}
