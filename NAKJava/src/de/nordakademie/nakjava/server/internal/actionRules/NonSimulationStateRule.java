package de.nordakademie.nakjava.server.internal.actionRules;

import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Sessions;

public abstract class NonSimulationStateRule extends StateRule {
	@Override
	public boolean isRuleApplicable(long sessionId, Player player) {
		return Sessions.getInstance().getSession(sessionId).getModel()
				.getSimulationModel() == null
				&& super.isRuleApplicable(sessionId, player);
	}

}
