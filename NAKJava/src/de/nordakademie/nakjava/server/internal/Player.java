package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.proxy.PlayerControl;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class Player {
	private PlayerState state;
	private PlayerControl control;
	private static List<Player> players = new ArrayList<>();

	public Player() {
		state = new PlayerState();
		control = new PlayerControl();
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

}
