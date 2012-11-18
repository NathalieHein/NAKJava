package de.nordakademie.nakjava.server.internal;

import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;

/**
 * singleton that is called to verify and commit a ServerAction
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class ActionBroker {
	private static ActionBroker instance;

	private ActionBroker() {
	}

	public synchronized static ActionBroker getInstance() {
		if (instance == null) {
			instance = new ActionBroker();
		}
		return instance;
	}

	/**
	 * verfies a serverAction. if succesful, the thread possesses the lock of
	 * the session afterwards for exclusive updating of session
	 * 
	 * @param serverAction
	 *            : the ServerAction to be verified
	 * @return true if verification succesful, false if not
	 */
	public boolean verify(ActionAbstractImpl serverAction) {

		if (serverAction.isServerInternal()) {
			return true;
		}
		Session session = Sessions.getInstance().getSession(
				serverAction.getSessionNr());
		session.lock();
		session = Sessions.getInstance()
				.getSession(serverAction.getSessionNr());

		if (session.verify(serverAction)) {
			return true;
		} else {
			session.releaseLock();
			return false;
		}

	}

	/**
	 * commits a ServerAction after this has finished updating and processing
	 * the session. Session might be deleted afterwards. the thread releases the
	 * lock on the Session.
	 * 
	 * @param serverAction
	 *            : The ServerAction to be commited
	 */
	public void commit(ActionAbstractImpl serverAction) {
		Session session = Sessions.getInstance().getSession(
				serverAction.getSessionNr());

		if (!checkSessionDeletion(serverAction.getSessionNr())) {
			session.commit();
			session.releaseLock();
		}
	}

	/**
	 * if Session is set to be deleted it will be removed from Sessions-list and
	 * the lock on the session will be released
	 * 
	 * @param sessionId
	 *            : The session to check for deletion
	 * @return true if session was deleted
	 */
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
