package de.nordakademie.nakjava.server.internal.actionRules.alternatingModeActionRules;

import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;

public abstract class AlternatingStateRule extends NonSimulationStateRule {

	@Override
	public boolean isRuleApplicable(long sessionId, Player player) {
		return Sessions.getInstance().getSession(sessionId).getActionInvoker() == player
				&& super.isRuleApplicable(sessionId, player);

	}
}
