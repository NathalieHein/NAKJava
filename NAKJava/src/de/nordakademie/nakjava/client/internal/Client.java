package de.nordakademie.nakjava.client.internal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.CheckIn;

public abstract class Client extends UnicastRemoteObject implements
		PlayerStateListener, PlayerControlListener {

	protected CheckIn checkIn;

	protected Client() throws RemoteException {
		super();

		try {
			checkIn = (CheckIn) Naming.lookup("//127.0.0.1/CheckIn");

			checkIn.register(this, this);

		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
