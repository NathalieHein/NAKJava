package de.nordakademie.nakjava.server.internal;

import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class Sessions {
	private static Sessions instance;
	private Map<Long, Session> sessions = new HashMap<>();
	private long nextSessionId = 1;

	private Sessions() {
	}

	/**
	 * {@link ActionBroker} ensures serializable transaction-handling for
	 * {@link ServerAction}s
	 * 
	 * @return
	 */
	public static Sessions getInstance() {
		if (instance == null) {
			instance = new Sessions();
		}
		return instance;
	}

	public long addPlayer(Player player) {
		for (Session session : sessions.values()) {
			// this check is in this class because Model does not know that
			// there is a session context
			if (session.isStillRoom()) {
				session.addPlayer(nextSessionId, player);
				return nextSessionId;
			}
		}
		nextSessionId++;
		sessions.put(nextSessionId, new Session(player));
		return nextSessionId;
	}

	public Map<Long, Session> getSessionMap() {
		return sessions;
	}
}
