package de.nordakademie.nakjava.client.bot;

import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public interface PlayerStateHook {
	public void newState(PlayerState state);
}
