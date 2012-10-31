package de.nordakademie.nakjava.server.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class Session {
	private int furtherAllowedNumberOfPlayers = 2;

	private Map<Player, PlayerState> playerToPlayerState = new HashMap<>();
	private Player actionInvoker;
	private long sessionId;

	private Lock lock = new ReentrantLock(true);

	public void commit() {
		for (Player player : getIteratorOfPlayers()) {
			if (!modeUnique || player == actionInvoker) {
				player.triggerChangeEvent();
			}
		}
		lock.unlock();
	}

	public Session(Player player) {
		players.add(player);
		// TODO state not to be set here!!!
		player.getGamelogicPlayer().setState(State.NEXT);
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
					// TODO needs to go back to Action.perform() here

				}
				actionInvoker = player;
				return true;
			}
		}
		return false;
	}

	public Session addPlayer(long sessionId, Player player) {
		if (furtherAllowedNumberOfPlayers > 0) {
			playerToPlayerState.put(player, null);
			furtherAllowedNumberOfPlayers--;
			this.sessionId = sessionId;
			// TODO questionable whether to set modeUnique or do it with an
			// action (+create a playercontrol message??)
			return this;
		}
		return new Session(player);
	}

	public Set<Player> getSetOfPlayers() {
		return playerToPlayerState.keySet();
	}

	public Map<Player, PlayerState> getPlayerToStateMap() {
		return playerToPlayerState;
	}

	public boolean isStillRoom() {
		return furtherAllowedNumberOfPlayers > 0;
	}

	public Player getActionInvoker() {
		return actionInvoker;
	}

	public void setActionInvoker(Player actionInvoker) {
		this.actionInvoker = actionInvoker;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

}
