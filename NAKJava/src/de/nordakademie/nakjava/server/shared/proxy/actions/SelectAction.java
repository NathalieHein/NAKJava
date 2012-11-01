package de.nordakademie.nakjava.server.shared.proxy.actions;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public abstract class SelectAction extends ActionContext {
	// TODO an Combo-Box dranhängen
	private String value;

	public SelectAction(String value, long batch, long sessionNr) {
		super(batch, sessionNr);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
