package de.nordakademie.nakjava.server.shared.proxy.actions;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

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
