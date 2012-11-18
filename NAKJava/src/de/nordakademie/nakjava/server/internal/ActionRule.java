package de.nordakademie.nakjava.server.internal;

import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * ActionRule to determine what actions are applicable at the current state
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public interface ActionRule {
	/**
	 * checks whether rule is applicalbe
	 * 
	 * @param sessionId
	 *            : unique identifier of session
	 * @param player
	 *            : player for whom actions are determined
	 * @return true if applicable, otherwise false
	 */
	public boolean isRuleApplicable(long sessionId, Player player);

	/**
	 * 
	 * @param sessionId
	 *            : unique identifier of session
	 * @param player
	 *            : player for whom actions are determined
	 * @return list of actions, might be empty
	 */
	public List<ActionContext> applyRule(long sessionId, Player player);
}
