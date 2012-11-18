package de.nordakademie.nakjava.server.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

/**
 * global singleton to administer all sessions; read/write lock necessary for
 * simultaneous access of multiple threads
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class Sessions {
	private static Sessions instance;
	private Map<Long, Session> sessions = new HashMap<>();
	private long nextSessionId = 1;
	private final ReadWriteLock rwlock = new ReentrantReadWriteLock(true);
	private final Lock readLock = rwlock.readLock();
	private final Lock writeLock = rwlock.writeLock();
	private final Condition writeCondition = writeLock.newCondition();

	private Sessions() {
	}

	/**
	 * {@link ActionBroker} ensures serializable transaction-handling for
	 * {@link ServerAction}s
	 * 
	 * @return
	 */
	public synchronized static Sessions getInstance() {
		if (instance == null) {
			instance = new Sessions();
		}
		return instance;
	}

	/**
	 * adds player to an either existing session or creatin a new one
	 * 
	 * @param player
	 * @return
	 */
	public synchronized long addPlayer(Player player) {
		readLock.lock();
		try {
			for (Session session : sessions.values()) {
				session.lock();
				if (session.addPlayer(player)) {
					return session.getSessionId();
				}
				session.releaseLock();
			}

		} finally {
			readLock.unlock();
		}
		writeLock.lock();
		try {
			nextSessionId++;
			Session newSession = new Session(player, nextSessionId);
			sessions.put(nextSessionId, newSession);
			newSession.lock();
			return newSession.getSessionId();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * deletion of session by removing it from global list
	 * 
	 * @param sessionId
	 *            : session to be removed
	 */
	public void deleteSession(long sessionId) {
		Session session = getSession(sessionId);
		writeLock.lock();
		try {
			session.lock();
			Model model = session.getModel();
			PlayerState self = model.getSelf();
			if (self != null && self.getName() != null) {
				Players.getInstance().removeLoggedInPlayerName(
						model.getSelf().getName());
			}
			PlayerState opponent = model.getOpponent();
			if (opponent != null) {
				Players.getInstance().removeLoggedInPlayerName(
						model.getOpponent().getName());
			}
			sessions.remove(sessionId);

		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * 
	 * @param sessionId
	 * @return: Session-object for param sessionId
	 */
	public Session getSession(long sessionId) {
		readLock.lock();
		try {
			return sessions.get(sessionId);
		} finally {
			readLock.unlock();
		}
	}
}
