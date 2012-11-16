package de.nordakademie.nakjava.client.bot;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public abstract class AbstractActionSelector {
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
