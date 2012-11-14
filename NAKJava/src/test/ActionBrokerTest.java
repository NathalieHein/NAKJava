package test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.nordakademie.nakjava.client.game.Client;
import de.nordakademie.nakjava.server.shared.proxy.CheckIn;
import de.nordakademie.nakjava.server.shared.proxy.CheckInImpl;

public class ActionBrokerTest {

	public static void main(String[] args) {
		(new ActionBrokerTest()).test();

	}

	public synchronized void test() {
		startServer();
		try {
			wait(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oneAfterEachOther();
		// sameTime();
	}

	public synchronized void oneAfterEachOther() {

		try {
			new Client();
			wait(300);
			new Client();
			wait(300);
			new Client();
			wait(300);
		} catch (RemoteException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sameTime() {
		ExecutorService threadpool = Executors.newFixedThreadPool(3);
		threadpool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					Client client1 = new Client();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		threadpool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					Client client2 = new Client();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		threadpool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					Client client3 = new Client();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	public static void startServer() {
		CheckInImpl.load();
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			CheckIn checkIn = new CheckInImpl();

			Naming.rebind("CheckIn", checkIn);
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Server started");
	}
}
