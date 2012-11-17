package de.nordakademie.nakjava.server.internal;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class Player {
	private PlayerState state;
	private PlayerControlListener control;

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

	public void triggerChangeEvent() {

		state.triggerChangeEvent();
	}

}
