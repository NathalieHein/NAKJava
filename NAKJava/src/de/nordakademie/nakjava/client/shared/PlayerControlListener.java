package de.nordakademie.nakjava.client.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerControlListener extends Remote {
	public void remoteClose() throws RemoteException;

	public void error(String text) throws RemoteException;
}
