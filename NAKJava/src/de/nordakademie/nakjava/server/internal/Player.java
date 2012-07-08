package de.nordakademie.nakjava.server.internal;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.client.shared.PlayerControlListener;
import de.nordakademie.nakjava.client.shared.PlayerStateListener;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControl;
import de.nordakademie.nakjava.server.shared.proxy.PlayerControlImpl;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class Player {
	private PlayerState state;
	private PlayerControl control;
	private static List<Player> players = new ArrayList<>();

	public Player(PlayerControlListener controlListener,
			PlayerStateListener stateListener) {
		state = new PlayerState(stateListener);
		try {
			control = new PlayerControlImpl(controlListener);
		} catch (RemoteException e) {
		}
		players.add(this);
	}

	public static List<Player> getPlayers() {
		return new ArrayList<Player>(players);
	}

	public PlayerState getState() {
		return state;
	}

	public PlayerControl getControl() {
		return control;
	}

	public void triggerChangeEvent() {
		control.triggerChangeEvent();
		state.triggerChangeEvent();
	}

}
