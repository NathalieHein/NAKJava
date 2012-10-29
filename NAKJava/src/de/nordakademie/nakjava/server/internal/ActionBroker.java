package de.nordakademie.nakjava.server.internal;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;

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

	// TODO comment missing
	public Model verify(ActionAbstractImpl serverAction) {
		lock.lock();
		Model mod = null;
		Map<Long, Model> sessions = Sessions.getInstance().getSessionMap();
		if (sessions.containsKey(serverAction.getSessionNr())) {
			if (sessions.get(serverAction.getSessionNr()).verify(serverAction)) {
				mod = sessions.get(serverAction.getSessionNr());
			}
		}

		// TODO
		/*
		 * for (Player player : Player.getPlayers()) { if
		 * (player.getState().getActions().contains(serverAction)) {
		 * Sessions.getInstance().setCurrentPlayer(player); return true; } }
		 */
		lock.unlock();
		return mod;
	}

	public void commit(ActionAbstractImpl serverAction) {
		// ensures that thread on Model.waitCondition has exclusive access for
		// rest of its verify()-Process
		lock.lock();
		// TODO kann hier in der Zwischenzeit die Session gel�scht worden sein?
		// Wenn ja, nochmal auf null �berpr�fen
		Sessions.getInstance().getSessionMap().get(serverAction.getSessionNr())
				.commit();
		lock.unlock();
	}

	public void releaseLock() {
		lock.unlock();
	}
}
