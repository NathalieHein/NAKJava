package de.nordakademie.nakjava.server.shared.proxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;

public class PlayerControlImpl extends UnicastRemoteObject implements
		PlayerControl {

	private PlayerControlListener controlListener;

	public PlayerControlImpl(PlayerControlListener controlListener)
			throws RemoteException {
		super();
		this.controlListener = controlListener;
	}

	@Override
	public void triggerChangeEvent() {
		// TODO Auto-generated method stub

	}

}
