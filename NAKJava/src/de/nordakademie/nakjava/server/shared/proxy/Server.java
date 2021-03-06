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

/**
 * Check-In for incoming clients
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class Server extends UnicastRemoteObject implements CheckIn {

	protected Server() throws RemoteException {
		super();
	}

	/**
	 * called by client at registration: creates Player-object for new Client
	 * with both listener-references; invokes InitAction that initializes Client
	 * on Server side
	 */
	@Override
	public void register(PlayerControlListener controlListener,
			PlayerStateListener stateListener) throws RemoteException {
		Player player = new Player(controlListener, stateListener);
		ActionContext initAction = new InitAction(player);
		initAction.perform();
	}

	/**
	 * main-method to start server: creates Registry, starts server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		load();
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			GlobalThreadPool.init(1);
			CheckIn checkIn = new Server();

			Naming.rebind("CheckIn", checkIn);
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}

		System.out.println("Server started");

	}

	/**
	 * invokes classpath-scanner on start of server
	 */
	private static void load() {
		ClasspathScanner.lookupAnnotatedScanners();
	}

}
