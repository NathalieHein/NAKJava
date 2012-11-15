package de.nordakademie.nakjava.server.internal.actionRules.alternatingModeActionRules;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;

public abstract class AlternatingStateRule extends NonSimulationStateRule {

	@Override
	public boolean isRuleApplicable(long sessionId, Player player) {
		PlayerState self = getSession(sessionId).getModel().getSelf();
		return self == getSession(sessionId).getPlayerStateForPlayer(player)
				&& super.isRuleApplicable(sessionId, player);

	}
}
