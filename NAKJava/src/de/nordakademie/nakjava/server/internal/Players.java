package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Players {
	private static Players instance;
	private List<String> playerNames;
	private final ReadWriteLock rwLock = new ReentrantReadWriteLock(true);
	private final Lock readLock = rwLock.readLock();
	private final Lock writeLock = rwLock.writeLock();

	private Players() {
		playerNames = new ArrayList<>();
	}

	public synchronized static Players getInstance() {
		if (instance == null) {
			instance = new Players();
		}
		return instance;
	}

	public List<String> getPlayerNames() {
		readLock.lock();
		try {
			return playerNames;
		} finally {
			readLock.unlock();
		}
	}

	public void addPlayerName(String name) {
		writeLock.lock();
		try {
			playerNames.add(name);
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
