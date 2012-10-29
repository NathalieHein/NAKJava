package de.nordakademie.nakjava.server.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.WinStrategy;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class Model {
	private int furtherAllowedNumberOfPlayers = 2;
	private boolean modeUnique;

	private List<Player> players = new LinkedList<>();
	private Player actionInvoker;
	private WinStrategy strategy;
	private long sessionId;

	private Lock lock = new ReentrantLock(true);

	public void commit() {
		for (Player player : players) {
			if (!modeUnique || player == actionInvoker) {
				player.triggerChangeEvent();
			}
		}
		lock.unlock();
	}

	public Model(Player player) {
		modeUnique = true;
		players.add(player);
		furtherAllowedNumberOfPlayers--;
		// TODO set of currentPlayer necessary??
	}

	public boolean verify(ServerAction serverAction) {
		for (Player player : players) {
			if (player.getState().getActions().contains(serverAction)) {
				// TODO works because commit(), that unlocks the lock is called
				// from a single-threaded context (ActionBroker->lock)
				if (!lock.tryLock()) {
					// TODO really NOT acceptable solution -> Model should not
					// know the ActionBroker
					// TODO releaseLock()+lock.lock() (->fall asleep) should be
					// one transaction: or making verify() synchronized???
					ActionBroker.getInstance().releaseLock();
					lock.lock();

				}
				actionInvoker = player;
				return true;
			}
		}
		return false;
	}

	public Model addPlayer(Player player) {
		if (furtherAllowedNumberOfPlayers > 0) {
			players.add(player);
			furtherAllowedNumberOfPlayers--;
			// TODO questionable whether to set modeUnique or do it with an
			// action (+create a playercontrol message??)
			modeUnique = true;
			return this;
		}
		return new Model(player);
	}

	public List<Player> getIterableListOfPlayers() {
		return players;
	}

	public boolean isStillRoom() {
		return furtherAllowedNumberOfPlayers > 0;
	}

	public boolean isModeUnique() {
		return modeUnique;
	}

	public void setModeUnique(boolean modeUnique) {
		this.modeUnique = modeUnique;
	}

	public Player getActionInvoker() {
		return actionInvoker;
	}

	public void setActionInvoker(Player actionInvoker) {
		this.actionInvoker = actionInvoker;
	}

	public WinStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(WinStrategy strategy) {
		this.strategy = strategy;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

}
