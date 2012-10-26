package de.nordakademie.nakjava.server.shared.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerAction extends Remote {
	public void perform() throws RemoteException;

	public long getSessionNr();
}
