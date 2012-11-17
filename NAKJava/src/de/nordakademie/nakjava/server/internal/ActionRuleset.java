package de.nordakademie.nakjava.server.internal;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClassLookup;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public final class ActionRuleset {
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
		Collections.sort(actions);
		for (ActionContext actionContext : actions) {
			System.out.println(actionContext.getClass().getSimpleName());
		}
		System.out.println();
		if (!actions.isEmpty()) {
			player.getState().setActions(actions);
		}

	}

	public static void update(long sessionId) {
		Session session = Sessions.getInstance().getSession(sessionId);
		session.getBatch().increaseBatchNr();
		for (Player player : session.getSetOfPlayers()) {
			player.getState().setBatch(session.getBatch().getCurrentBatchNr());
			updatePlayerActions(player, sessionId);
		}
	}

	@ClassLookup
	private static void createActionRules() {
		actionRules = new LinkedList<>();
		List<Class<ActionRule>> actionRuleClasses = ClasspathScanner
				.findClasses(
						"de.nordakademie.nakjava.server.internal.actionRules",
						"de.nordakademie.nakjava.actionRulePackage",
						new ClassAcceptor<ActionRule>() {

							@Override
							public boolean acceptClass(Class<ActionRule> clazz) {
								return !Modifier.isAbstract(clazz
										.getModifiers());
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
