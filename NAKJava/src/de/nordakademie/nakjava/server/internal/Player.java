package de.nordakademie.nakjava.server.internal;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

/**
 * representation of client with reference to client's listeners
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class Player {
	private PlayerState state;
	private PlayerControlListener control;

	/**
	 * sets client's listeners
	 * 
	 * @param controlListener
	 *            : for control messages
	 * @param stateListener
	 *            : for game-related messages
	 */
	public Player(PlayerControlListener controlListener,
			PlayerStateListener stateListener) {

		control = controlListener;
		state = new PlayerState(stateListener);
	}

	public PlayerState getState() {
		return state;
	}

	public PlayerControlListener getControl() {
		return control;
	}

	/**
	 * triggers client of change in its model or actions
	 */
	public void triggerChangeEvent() {

		state.triggerChangeEvent();
	}

}
