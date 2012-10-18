package de.nordakademie.nakjava.server.internal;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

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

	public boolean verify(ServerAction serverAction) {
		lock.lock();
		for (Player player : Player.getPlayers()) {
			if (player.getState().getActions().contains(serverAction)) {
				Model.getInstance().setCurrentPlayer(player);
				return true;
			}
		}
		lock.unlock();
		return false;
	}

	public void commit() {
		for (Player player : Player.getPlayers()) {
			if (!Model.getInstance().isModeUnique()
					|| player == Model.getInstance().getCurrentPlayer()) {
				player.triggerChangeEvent();
			}
		}
		lock.unlock();
	}
}
