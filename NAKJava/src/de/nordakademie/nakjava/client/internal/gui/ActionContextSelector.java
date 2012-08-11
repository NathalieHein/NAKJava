package de.nordakademie.nakjava.client.internal.gui;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public interface ActionContextSelector {
	public boolean select(ActionContext context);
}
