package de.nordakademie.nakjava.server.shared.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for Proxy-ServerActions that process a client's request on server's
 * side
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public interface ServerAction extends Remote {
	/**
	 * single method called for processing client's request on server
	 * 
	 * @throws RemoteException
	 */
	public void perform() throws RemoteException;
}
