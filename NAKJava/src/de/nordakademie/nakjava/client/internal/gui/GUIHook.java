package de.nordakademie.nakjava.client.internal.gui;

import de.nordakademie.nakjava.server.shared.serial.PlayerState;

/**
 * Interface for a gui.
 * 
 */
public interface GUIHook {
	/**
	 * Everytime a new state is received it will be passed to the gui
	 * 
	 * @param state
	 */
	public void newState(PlayerState state);

	/**
	 * Before a connection to the server is established, this method is invoked.
	 */
	public void preCheckin();

	/**
	 * When receiving an error message from the server, this method is invoked.
	 * 
	 * @param text
	 */
	public void error(String text);
}
