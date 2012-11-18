package de.nordakademie.nakjava.server.internal.actionRules.alternatingModeActionRules;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;

/**
 * AbstractClass for all StateRules where the turn of players is predetermined
 * (mainly in the game). Only the currentPlayer(model.self) is applicable for
 * these rules
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public abstract class AlternatingStateRule extends StateRule {

	@Override
	public boolean isRuleApplicable(long sessionId, Player player) {
		PlayerState self = getSession(sessionId).getModel().getSelf();
		return self == getSession(sessionId).getPlayerStateForPlayer(player)
				&& super.isRuleApplicable(sessionId, player);

	}
}
