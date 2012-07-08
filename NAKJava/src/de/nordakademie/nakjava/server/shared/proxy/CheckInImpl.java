package de.nordakademie.nakjava.server.shared.proxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.internal.Player;

public class CheckInImpl extends UnicastRemoteObject implements CheckIn {

	protected CheckInImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void register(PlayerControlListener controlListener,
			PlayerStateListener stateListener) throws RemoteException {
		new Player(controlListener, stateListener);

		stateListener.stateChanged(null);
	}

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			CheckIn checkIn = new CheckInImpl();

			Naming.rebind("CheckIn", checkIn);
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
