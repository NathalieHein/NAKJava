package de.nordakademie.nakjava.server.shared.proxy;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;

public abstract class Action implements Remote, Serializable {

	public void perform() throws RemoteException {
		// TODO verify aufrufen im Action Broker
		// TODO performImpl
		// TODO update über ActionRuleset, VisibleModleUpdater
		// TODO commit beim ActionBroker (damit wieder Freigabe)
	}

	protected abstract void performImpl(Model mod);

}
