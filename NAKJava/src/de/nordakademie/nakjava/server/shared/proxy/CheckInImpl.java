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
import de.nordakademie.nakjava.server.shared.proxy.actions.InitAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.util.GlobalThreadPool;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class CheckInImpl extends UnicastRemoteObject implements CheckIn {

	protected CheckInImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void register(PlayerControlListener controlListener,
			PlayerStateListener stateListener) throws RemoteException {
		Player player = new Player(controlListener, stateListener);
		// TODO this is not fine yet
		ActionContext initAction = new InitAction(player);
		initAction.perform();
	}

	public static void main(String[] args) {
		load();
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			GlobalThreadPool.init(1);
			CheckIn checkIn = new CheckInImpl();

			Naming.rebind("CheckIn", checkIn);
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Server started");

	}

	private static void load() {
		ClasspathScanner.lookupAnnotatedScanners();
	}

}
