package de.nordakademie.nakjava.server.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

/**
 * Session that includes a list of players(could be more than two for a
 * different game) in one game and a model with game-specific information
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class Session {
	private int furtherAllowedNumberOfPlayers = 2;

	private Map<Player, PlayerState> playerToPlayerState = new HashMap<>();
	private Model model;
	private Player actionInvoker;
	private Batch batch;
	private long sessionId;

	private final Lock lock = new ReentrantLock();
	private boolean toBeDeleted;

	/**
	 * locks session for updating
	 */
	public void lock() {
		lock.lock();
	}

	/**
	 * releases session lock
	 */
	public void releaseLock() {
		lock.unlock();
	}

	/**
	 * triggers client's notification of change
	 */
	public void commit() {
		for (Player player : getSetOfPlayers()) {
			player.triggerChangeEvent();
		}
	}

	/**
	 * creates new session with a new batch and decreases number of allowed
	 * players
	 * 
	 * @param player
	 *            : first Player to be added to session
	 * @param sessionId
	 *            : unique identification of session
	 */
	public Session(Player player, long sessionId) {

		actionInvoker = player;
		batch = new Batch();
		PlayerState playerState = new PlayerState();
		model = new Model(playerState);
		playerToPlayerState.put(player, playerState);
		furtherAllowedNumberOfPlayers--;
		setToBeDeleted(false);
		this.sessionId = sessionId;
	}

	/**
	 * checks if last player to invoke an action whose turn it currently is
	 * 
	 * @return:
	 */
	public boolean isActionInvokerCurrentPlayer() {
		return playerToPlayerState.get(actionInvoker) == model.getSelf();
	}

	/**
	 * verifies if serverAction belongs to one of this session's players and is
	 * not too old
	 * 
	 * @param serverAction
	 *            : action to be verified
	 * @return
	 */
	public boolean verify(ServerAction serverAction) {
		for (Player player : getSetOfPlayers()) {
			if (player.getState().getActions().contains(serverAction)) {
				actionInvoker = player;
				return true;
			}
		}
		return false;
	}

	/**
	 * adds player to current session
	 * 
	 * @param player
	 * @return
	 */
	public boolean addPlayer(Player player) {
		if (furtherAllowedNumberOfPlayers > 0) {
			actionInvoker = player;
			PlayerState playerState = new PlayerState();
			playerToPlayerState.put(player, playerState);
			model.addPlayerState(playerState);
			furtherAllowedNumberOfPlayers--;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param player
	 * @return any other player that is not the playe in the parameter, if there
	 *         is none, return null
	 */
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

	public long getSessionId() {
		return sessionId;
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

	/**
	 * removes from session last player that invoked an action
	 */
	public void removeActionInvoker() {
		furtherAllowedNumberOfPlayers++;
		playerToPlayerState.remove(actionInvoker);
		actionInvoker = getOneOtherPlayer(actionInvoker);
		model.removeSelf();

	}

}
