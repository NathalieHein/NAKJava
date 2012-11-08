package de.nordakademie.nakjava.server.internal.actionRules.simulationModeActionRules;

import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;

public abstract class SimulationStateRule extends StateRule {
	public boolean isRuleApplicable(long sessionId, Player player) {
		return Sessions.getInstance().getSession(sessionId).getModel()
				.getSimulationModel() != null
				&& super.isRuleApplicable(sessionId, player);
	}
}
