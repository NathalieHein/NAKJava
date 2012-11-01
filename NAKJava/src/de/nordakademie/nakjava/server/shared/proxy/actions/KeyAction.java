package de.nordakademie.nakjava.server.shared.proxy.actions;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public abstract class KeyAction extends ActionContext {

	private char key;

	public KeyAction(char key, long batch, long sessionNr) {
		super(batch, sessionNr);
		this.key = key;
	}

	public char getKey() {
		return key;
	}

}
