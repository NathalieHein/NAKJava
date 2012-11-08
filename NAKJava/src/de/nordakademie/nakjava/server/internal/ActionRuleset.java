package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ActionRuleset {
	// TODO how do actionRules get here? -> scan classpath
	private static List<ActionRule> actionRules;

	// Suppresses default constructor for noninstantiability
	private ActionRuleset() {
		throw new AssertionError();
	}

	private static void updatePlayerActions(Player player, long sessionId) {
		List<ActionContext> actions = new ArrayList<>();
		for (ActionRule actionRule : actionRules) {
			if (actionRule.isRuleApplicable(sessionId, player)) {
				actions.addAll(actionRule.applyRule(sessionId, player));
			}
		}
		player.getState().setActions(actions);

	}

	public static void update(long sessionId) {
		Session session = Sessions.getInstance().getSession(sessionId);
		for (Player player : session.getSetOfPlayers()) {
			updatePlayerActions(player, sessionId);
		}
	}
}
