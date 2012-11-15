package de.nordakademie.nakjava.server.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

//No need for synchronization because Action Broker ensures that all Session-methods are called either from a single-threaded context or are only read operations 
public class Session {
	private int furtherAllowedNumberOfPlayers = 2;

	private Map<Player, PlayerState> playerToPlayerState = new HashMap<>();
	private Model model;
	private Player actionInvoker;
	private Batch batch;

	private final Lock lock = new ReentrantLock();
	private boolean toBeDeleted;

	public void lock() {
		lock.lock();
	}

	public void releaseLock() {
		lock.unlock();
	}

	public void commit() {
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
		PlayerState playerState = new PlayerState();
		model = new Model(playerState);
		playerToPlayerState.put(player, playerState);
		furtherAllowedNumberOfPlayers--;
		setToBeDeleted(false);
	}

	public boolean isActionInvokerCurrentPlayer() {
		return playerToPlayerState.get(actionInvoker) == model.getSelf();
	}

	public boolean verify(ServerAction serverAction) {
		for (Player player : getSetOfPlayers()) {
			if (player.getState().getActions().contains(serverAction)) {
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

	public boolean isToBeDeleted() {
		return toBeDeleted;
	}

	public void setToBeDeleted(boolean toBeDeleted) {
		this.toBeDeleted = toBeDeleted;
	}

}
