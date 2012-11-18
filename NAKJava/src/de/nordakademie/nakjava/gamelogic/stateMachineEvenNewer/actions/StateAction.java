package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.server.internal.model.Model;

/**
 * A StateAction is performed at a change of state of a player
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public interface StateAction {
	/**
	 * StateAction performs model-changes when the state of a player changes
	 * 
	 * @param model
	 *            : the model to be processed
	 */
	public void perform(Model model);
}
