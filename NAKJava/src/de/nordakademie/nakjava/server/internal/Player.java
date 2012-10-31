package de.nordakademie.nakjava.server.internal;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControl;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControlImpl;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class Player {
	private PlayerState state;
	private PlayerControl control;

	public Player(PlayerControlListener controlListener,
			PlayerStateListener stateListener) {

		try {
			control = new PlayerControlImpl(controlListener);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		state = new PlayerState(stateListener);
		long sessionId = Sessions.getInstance().addPlayer(this);
		state.initialize(sessionId);

	}

	public PlayerState getState() {
		return state;
	}

	public PlayerControl getControl() {
		return control;
	}

	public void triggerChangeEvent() {
		// TODO is it here where Exceptions occur??
		state.triggerChangeEvent();
		control.triggerChangeEvent();
	}

	public de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState getGamelogicPlayer() {
		return gamelogicPlayer;
	}

	public void setGamelogicPlayer(
			de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState gamelogicPlayer) {
		this.gamelogicPlayer = gamelogicPlayer;
	}

}
