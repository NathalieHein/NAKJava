package de.nordakademie.nakjava.server.shared.proxy.actions;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Abstract ActionContext for all ActionContexts that contain text input
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public abstract class KeyAction extends ActionContext {

	private char key;

	public KeyAction(char key, long sessionNr) {
		super(sessionNr);
		this.key = key;
	}

	public char getKey() {
		return key;
	}

}
