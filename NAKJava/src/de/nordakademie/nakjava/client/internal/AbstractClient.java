package de.nordakademie.nakjava.client.internal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

import de.nordakademie.nakjava.client.internal.gui.GUIHook;
import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.CheckIn;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;
import de.nordakademie.nakjava.util.GlobalThreadPool;
import de.nordakademie.nakjava.util.StringUtilities;

/**
 * Abstract base class for clients. This class manages the connection and login
 * to the server. A different ip-address for a server can be passed via
 * property: de.nordakademie.nakjava.serverIP=""
 * 
 * @author Kai
 * 
 */
public abstract class AbstractClient extends UnicastRemoteObject implements
		PlayerStateListener, PlayerControlListener {

	protected CheckIn checkIn;

	protected long batchNr;
	protected Lock stateChangeLock;

	protected GUIHook gui;

	/**
	 * Base constructor, a gui hook can be passed. This gui hook is passed the
	 * new states from the server.
	 * 
	 * @param gui
	 * @throws RemoteException
	 */
	protected AbstractClient(GUIHook gui) throws RemoteException {
		super();
		this.gui = gui;
		batchNr = Long.MIN_VALUE;
		stateChangeLock = new ReentrantLock(true);

		if (gui != null) {
			gui.preCheckin();
		}
		preCheckinImpl();

		try {
			String differntIP = System
					.getProperty("de.nordakademie.nakjava.serverIP");
			if (StringUtilities.isNotNullOrEmpty(differntIP)) {
				checkIn = (CheckIn) Naming.lookup("//" + differntIP
						+ "/CheckIn");
			} else {
				checkIn = (CheckIn) Naming.lookup("//127.0.0.1/CheckIn");
			}

			checkIn.register(this, this);

		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@Override
	public final void stateChanged(final PlayerState state)
			throws RemoteException {

		GlobalThreadPool.perform(new Runnable() {

			@Override
			public void run() {
				stateChangeLock.lock();

				if (batchNr < state.getBatch()) {
					batchNr = state.getBatch();
					if (gui != null) {
						gui.newState(state);
					}
					stateChange(state);
				}

				stateChangeLock.unlock();
			}
		});

	}

	@Override
	public void remoteClose() throws RemoteException {
		final Lock lock = new ReentrantLock();
		lock.lock();
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					lock.lock();
					System.exit(0);
					lock.unlock();
				}
			}).start();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void error(String text) throws RemoteException {
		if (gui != null) {
			gui.error(text);
		}

	}

	private void preCheckinImpl() {
		GlobalThreadPool.init(3);
		preCheckin();
	}

	/**
	 * Subclasses may override this method in order to initialize before
	 * connection to the server is established
	 */
	protected void preCheckin() {

	}

	/**
	 * Subclasses may override in order to add specific behaviour for states.
	 * 
	 * @param state
	 */
	protected void stateChange(PlayerState state) {

	}
}
