package de.nordakademie.nakjava.client.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public interface PlayerStateListener extends Remote {

	public void stateChanged(PlayerState state) throws RemoteException;
}
