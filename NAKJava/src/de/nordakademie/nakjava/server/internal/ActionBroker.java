package de.nordakademie.nakjava.server.internal;

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
	public synchronized boolean verify(ActionAbstractImpl serverAction) {
		if (serverAction.isServerInternal()) {
			return true;
		}
		Session session = Sessions.getInstance().getSession(
				serverAction.getSessionNr());
		if (session != null) {
			if (session.verify(serverAction)) {
				lock.unlock();
				return true;
			}
		}

		lock.unlock();
		return false;
	}

	public synchronized void commit(ActionAbstractImpl serverAction) {
		// ensures that thread on Model.waitCondition has exclusive access for
		// rest of its verify()-Process
		lock.lock();
		// TODO kann hier in der Zwischenzeit die Session gel�scht worden sein?
		// Wenn ja, nochmal auf null �berpr�fen
		if (serverAction.isServerInternal()) {
			Sessions.getInstance().getSession(serverAction.getSessionNr())
					.commitWithOutLock();
		} else {
			Sessions.getInstance().getSession(serverAction.getSessionNr())
					.commit();
		}
		lock.unlock();
	}

	// TODO not working with sessionId anymore but only passing real reference
	// around
	public synchronized Session getSession(long sessionId) {
		return Sessions.getInstance().getSession(sessionId);
	}
}
