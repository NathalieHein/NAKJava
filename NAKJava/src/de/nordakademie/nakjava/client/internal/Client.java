package de.nordakademie.nakjava.client.internal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.CheckIn;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class Client extends UnicastRemoteObject implements PlayerStateListener,
		PlayerControlListener {

	protected Client() throws RemoteException {
		super();
	}

	@Override
	public void stateChanged(PlayerState state) throws RemoteException {
		System.out.println("klappt!!!!");
	}

	public static void main(String[] args) {
		try {
			CheckIn checkIn = (CheckIn) Naming.lookup("//127.0.0.1/CheckIn");

			Client client = new Client();

			checkIn.register(client, client);

		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
