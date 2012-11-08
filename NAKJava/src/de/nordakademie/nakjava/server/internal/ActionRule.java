package de.nordakademie.nakjava.server.internal;

import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

//TODO What does an ActionRule really need, imho state and player is enough....
public interface ActionRule {

	public boolean isRuleApplicable(long sessionId, Player player);

	public List<ActionContext> applyRule(long sessionId, Player player);
}
