package de.nordakademie.nakjava.server.shared.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;

public interface CheckIn extends Remote {
	public void register(PlayerControlListener controlListener,
			PlayerStateListener stateListener) throws RemoteException;
}
