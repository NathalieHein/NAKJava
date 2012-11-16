package de.nordakademie.nakjava.client.internal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.CheckIn;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public abstract class Client extends UnicastRemoteObject implements
		PlayerStateListener, PlayerControlListener {

	protected CheckIn checkIn;
	protected final List<Object> preCheckinFinalValues;

	protected long batchNr;
	protected Lock stateChangeLock;

	protected Client() throws RemoteException {
		super();
		batchNr = Long.MIN_VALUE;
		stateChangeLock = new ReentrantLock(true);

		preCheckinFinalValues = new LinkedList<>();
		preCheckin();

		try {
			checkIn = (CheckIn) Naming.lookup("//127.0.0.1/CheckIn");

			checkIn.register(this, this);

		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract void stateChange(PlayerState state);

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
					stateChange(state);
				}

				stateChangeLock.unlock();
			}
		}).start();

	}

	protected abstract void preCheckin();
}
