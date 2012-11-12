package de.nordakademie.nakjava.server.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class Session {
	private int furtherAllowedNumberOfPlayers = 2;

	private Map<Player, PlayerState> playerToPlayerState = new HashMap<>();
	private Model model;
	private Player actionInvoker;
	private Batch batch;

	private Lock lock = new ReentrantLock(true);

	public void commit() {
		for (Player player : getSetOfPlayers()) {
			// TODO dirtyBits to be set correctly
			// if (!modeUnique || player == actionInvoker) {
			player.triggerChangeEvent();
			// }
		}
		lock.unlock();
	}

	public void commitWithOutLock() {
		for (Player player : getSetOfPlayers()) {
			// TODO dirtyBits to be set correctly
			// if (!modeUnique || player == actionInvoker) {
			player.triggerChangeEvent();
			// }
		}
	}

	public Session(Player player) {

		actionInvoker = player;
		batch = new Batch();
		// TODO where to get initial artifacts
		PlayerState playerState = new PlayerState();
		model = new Model(playerState);
		playerToPlayerState.put(player, playerState);
		furtherAllowedNumberOfPlayers--;
	}

	public boolean isActionInvokerCurrentPlayer() {
		return playerToPlayerState.get(actionInvoker) == model.getSelf();
	}

	public boolean verify(ServerAction serverAction) {
		for (Player player : getSetOfPlayers()) {
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

	public Session addPlayer(Player player) {
		if (furtherAllowedNumberOfPlayers > 0) {
			actionInvoker = player;
			PlayerState playerState = new PlayerState();
			playerToPlayerState.put(player, playerState);
			model.addPlayerState(playerState);
			furtherAllowedNumberOfPlayers--;
			// TODO questionable whether to set modeUnique or do it with an
			// action (+create a playercontrol message??)
			return this;
		}
		return new Session(player);
	}

	public Player getOneOtherPlayer(Player player) {
		for (Player otherPlayer : playerToPlayerState.keySet()) {
			if (player != otherPlayer) {
				return otherPlayer;
			}
		}
		return null;
	}

	public PlayerState getPlayerStateForPlayer(Player player) {
		return playerToPlayerState.get(player);
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

	public Model getModel() {
		return model;
	}

	public Batch getBatch() {
		return batch;
	}

	public Set<Player> getSetOfPlayers() {
		return playerToPlayerState.keySet();
	}

}
