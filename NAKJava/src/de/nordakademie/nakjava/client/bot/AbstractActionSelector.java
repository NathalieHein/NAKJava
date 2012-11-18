package de.nordakademie.nakjava.client.bot;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Utility class for selecting actions with a certain structure from a List of
 * actions. The desired structure is passed with an
 * {@link ActionContextSelector}
 * 
 * @author Kai
 * 
 */
public abstract class AbstractActionSelector {
	/**
	 * Selects a single action (first hit of {@link ActionContextSelector}).
	 * otherwise <code>null</code>
	 * 
	 * @param actions
	 * @param selector
	 * @return action selected by selector, <code>null</code> otherwise
	 */
	public static ActionContext selectAction(List<ActionContext> actions,
			ActionContextSelector selector) {
		ActionContext result = null;
		for (ActionContext action : actions) {
			if (selector.select(action)) {
				result = action;
				break;
			}
		}
		return result;
	}

	/**
	 * Selects all actions fitting the {@link ActionContextSelector}
	 * 
	 * @param actions
	 * @param selector
	 * @return List of actions, empty if no hit by selector
	 */
	public static List<ActionContext> selectActions(
			List<ActionContext> actions, ActionContextSelector selector) {
		List<ActionContext> result = new LinkedList<>();

		for (ActionContext action : actions) {
			if (selector.select(action)) {
				result.add(action);
			}
		}
		return result;
	}
}
