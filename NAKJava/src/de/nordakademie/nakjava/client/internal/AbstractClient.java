package de.nordakademie.nakjava.client.internal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.client.internal.gui.GUIHook;
import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.CheckIn;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public abstract class AbstractClient extends UnicastRemoteObject implements
		PlayerStateListener, PlayerControlListener {

	protected CheckIn checkIn;

	protected long batchNr;
	protected Lock stateChangeLock;

	protected GUIHook gui;

	protected AbstractClient(GUIHook gui) throws RemoteException {
		super();
		this.gui = gui;
		batchNr = Long.MIN_VALUE;
		stateChangeLock = new ReentrantLock(true);

		if (gui != null) {
			gui.preCheckin();
		}
		preCheckin();

		try {
			checkIn = (CheckIn) Naming.lookup("//127.0.0.1/CheckIn");

			checkIn.register(this, this);

		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public final void stateChanged(final PlayerState state)
			throws RemoteException {
		// TODO perhaps a Thread pool here later
		new Thread(new Runnable() {

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
		}).start();

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

	protected void preCheckin() {

	}

	protected void stateChange(PlayerState state) {

	}
}
