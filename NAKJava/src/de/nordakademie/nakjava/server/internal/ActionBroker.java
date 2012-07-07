package de.nordakademie.nakjava.server.internal;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.server.shared.proxy.Action;

public class ActionBroker {
	private static ActionBroker instance;
	private Lock lock;

	private ActionBroker() {
		lock = new ReentrantLock(true);
	}

	public synchronized static ActionBroker getInstance() {
		if (instance == null) {
			instance = new ActionBroker();
		}
		return instance;
	}

	public boolean verify(Action action) {
		lock.lock();
		for (Player player : Player.getPlayers()) {
			if (player.getState().getActions().contains(action)) {
				return true;
			}
		}
		lock.unlock();
		return false;
	}

	public void commit() {
		lock.unlock();
	}
}
