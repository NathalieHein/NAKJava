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

		if (serverAction.isServerInternal()) {
			return true;
		}
		Session session = Sessions.getInstance().getSession(
				serverAction.getSessionNr());
		session.lock();
		// TODO does a problem with the write-lock in sessions occur
		// TODO here -> definetly waiting quite a while
		// session might be deleted in the meantime
		session = Sessions.getInstance()
				.getSession(serverAction.getSessionNr());

		if (session.verify(serverAction)) {
			System.out.println("no drop");
			return true;
		} else {
			System.out.println("drop");
			session.releaseLock();
			return false;
		}

	}

	public void commit(ActionAbstractImpl serverAction) {
		// ensures that thread on Model.waitCondition has exclusive access for
		// rest of its verify()-Process
		// TODO kann hier in der Zwischenzeit die Session gel�scht worden
		// sein?
		// Wenn ja, nochmal auf null �berpr�fen -> should not be possible
		// TODO might be stuck in ReadLock of Sessions
		Session session = Sessions.getInstance().getSession(
				serverAction.getSessionNr());

		if (!checkSessionDeletion(serverAction.getSessionNr())) {
			session.releaseLock();
			session.commit();
		}
	}

	private boolean checkSessionDeletion(long sessionId) {
		Session session = Sessions.getInstance().getSession(sessionId);
		if (session.isToBeDeleted()) {
			Sessions.getInstance().deleteSession(sessionId);
			session.releaseLock();
			return true;
		}
		return false;
	}
}
