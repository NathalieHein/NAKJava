package de.nordakademie.nakjava.server.internal;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * global list of player names that need to be unique over all sessions;
 * provides read/write lock for simultaneous access of multiple threads
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class Players {
	private static Players instance;
	private Set<String> loggendInPlayerNames;
	private Set<String> reservedPlayerNames;
	private final Lock loggedInLock = new ReentrantLock(true);
	private final Lock reservedLock = new ReentrantLock(true);

	private Players() {
		loggendInPlayerNames = new HashSet<>();
		reservedPlayerNames = new HashSet<>();
	}

	public synchronized static Players getInstance() {
		if (instance == null) {
			instance = new Players();
		}
		return instance;
	}

	public boolean reservePlayerName(String name) {
		reservedLock.lock();
		loggedInLock.lock();
		try {
			boolean playerNameExistsAlready = reservedPlayerNames
					.contains(name) || loggendInPlayerNames.contains(name);
			if (!playerNameExistsAlready) {
				reservedPlayerNames.add(name);
			}
			return !playerNameExistsAlready;
		} finally {
			loggedInLock.unlock();
			reservedLock.unlock();
		}
	}

	public void removeReservedPlayerName(String name) {
		reservedLock.lock();
		try {
			reservedPlayerNames.remove(name);
		} finally {
			reservedLock.unlock();
		}

	}

	public void logInPlayerName(String name) {
		reservedLock.lock();
		loggedInLock.lock();
		try {
			loggendInPlayerNames.add(name);
			reservedPlayerNames.remove(name);
		} finally {
			loggedInLock.unlock();
			reservedLock.unlock();
		}
	}

	public void removeLoggedInPlayerName(String name) {
		loggedInLock.lock();
		try {
			loggendInPlayerNames.remove(name);
		} finally {
			loggedInLock.unlock();
		}
	}

}
