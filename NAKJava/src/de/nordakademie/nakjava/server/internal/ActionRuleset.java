package de.nordakademie.nakjava.server.internal;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class ActionRuleset {
	private static List<ActionRule> actionRules;

	// Suppresses default constructor for noninstantiability
	private ActionRuleset() {
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

	public static void createActionRulesetInstance() {
		actionRules = new LinkedList<>();
		List<Class<ActionRule>> actionRuleClasses = ClasspathScanner
				.findClasses(
						"de.nordakademie.nakjava.server.internal.actionRules",
						"de.nordakademie.nakjava.actionRulePackage",
						new ClassAcceptor<ActionRule>() {

							@Override
							public boolean acceptClass(Class<ActionRule> clazz) {
								return !Modifier.isFinal(clazz.getModifiers());
							}
						}, ActionRule.class);

		for (Class<ActionRule> clazz : actionRuleClasses) {
			try {
				actionRules.add(clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

}
