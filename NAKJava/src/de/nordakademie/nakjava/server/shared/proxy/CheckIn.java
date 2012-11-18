package de.nordakademie.nakjava.server.shared.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;

/**
 * check-in for incoming Clients
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public interface CheckIn extends Remote {
	/**
	 * 
	 * @param controlListener
	 *            : reference to client side: used for exchanging non-game
	 *            control messages
	 * @param stateListener
	 *            : reference to client side: used for exchanging game-specific
	 *            messages like states or actions
	 * @throws RemoteException
	 */
	public void register(PlayerControlListener controlListener,
			PlayerStateListener stateListener) throws RemoteException;
}
