package de.nordakademie.nakjava.server.shared.proxy.actions;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Abstract ActionContext for all ActionContexts that contain a choice between
 * string values
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public abstract class SelectAction extends ActionContext {
	private String value;

	public SelectAction(String value, long sessionNr) {
		super(sessionNr);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
