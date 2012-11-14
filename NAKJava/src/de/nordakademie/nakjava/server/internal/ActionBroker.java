package de.nordakademie.nakjava.server.internal;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;

public class ActionBroker {
	private static ActionBroker instance;
	private final Lock lock = new ReentrantLock(true);

	private ActionBroker() {
	}

	public synchronized static ActionBroker getInstance() {
		if (instance == null) {
			instance = new ActionBroker();
		}
		return instance;
	}

	// TODO comment missing
	public boolean verify(ActionAbstractImpl serverAction) {
		lock.lock();

		try {
			if (serverAction.isServerInternal()) {
				return true;
			}
			Session session = Sessions.getInstance().getSession(
					serverAction.getSessionNr());
			while (session != null && !session.tryLock()) {
				session.await();
				// TODO does a problem with the write-lock in sessions occur
				// TODO here -> definetly waiting quite a while
				// session might be deleted in the meantime
				session = Sessions.getInstance().getSession(
						serverAction.getSessionNr());
			}

			if (session.verify(serverAction)) {
				return true;
			} else {
				session.releaseLock();
				return false;
			}

		} finally {
			lock.unlock();
		}
	}

	public void commit(ActionAbstractImpl serverAction) {
		// ensures that thread on Model.waitCondition has exclusive access for
		// rest of its verify()-Process
		lock.lock();
		try {
			// TODO kann hier in der Zwischenzeit die Session gel�scht worden
			// sein?
			// Wenn ja, nochmal auf null �berpr�fen -> should not be possible
			// TODO might be stuck in ReadLock of Sessions
			Session session = Sessions.getInstance().getSession(
					serverAction.getSessionNr());

			if (!checkSessionDeletion(serverAction.getSessionNr())) {
				session.commit();
			}
			if (!serverAction.isServerInternal()) {
				session.signal();
				session.releaseLock();
			}
		} finally {
			lock.unlock();
		}
	}

	private boolean checkSessionDeletion(long sessionId) {
		Session session = Sessions.getInstance().getSession(sessionId);
		if (session.isToBeDeleted()) {
			Sessions.getInstance().deleteSession(sessionId);
			session.tryLock();
			return true;
		}
		return false;
	}

	// TODO not working with sessionId anymore but only passing real reference
	// around
	public synchronized Session getSession(long sessionId) {
		return Sessions.getInstance().getSession(sessionId);
	}
}
