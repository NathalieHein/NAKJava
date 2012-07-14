package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.shared.proxy.Action;

public abstract class ActionContext implements Serializable {
	// TODO: was tun wir hier???

	protected Action action;

	protected ActionContext() {
		try {
			action = getAction();
		} catch (RemoteException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract Action getAction() throws RemoteException;

	public void perform() throws RemoteException {
		action.perform();
	}

	@Override
	public boolean equals(Object obj) {
		return action.equals(obj);
	}
}
