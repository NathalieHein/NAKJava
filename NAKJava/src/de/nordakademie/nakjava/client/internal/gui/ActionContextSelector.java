package de.nordakademie.nakjava.client.internal.gui;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Interface for passing a selector as anonymous type to
 * {@link ActionContextHolder}s.
 * 
 * @author Kai
 * 
 */
public interface ActionContextSelector {
	/**
	 * Returns wheter this actoincontext is feasible
	 * 
	 * @param context
	 * @return
	 */
	public boolean select(ActionContext context);
}
