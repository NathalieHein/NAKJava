package de.nordakademie.nakjava.client.internal.gui;

import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public interface GUIHook {
	public void newState(PlayerState state);

	public void preCheckin();

	public void error(String text);
}
