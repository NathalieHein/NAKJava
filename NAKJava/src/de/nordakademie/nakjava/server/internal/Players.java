package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Players {
	private static Players instance;
	private List<String> playerNames;
	private final Lock writeLock = new ReentrantLock(true);

	private Players() {
		playerNames = new ArrayList<>();
	}

	public synchronized static Players getInstance() {
		if (instance == null) {
			instance = new Players();
		}
		return instance;
	}

	public boolean addPlayerName(String name) {
		writeLock.lock();
		try {
			boolean contains = playerNames.contains(name);
			playerNames.add(name);
			return !contains;
		} finally {
			writeLock.unlock();
		}
	}

	public void removePlayerName(String name) {
		writeLock.lock();
		try {
			playerNames.remove(name);
		} finally {
			writeLock.unlock();
		}
	}

}
