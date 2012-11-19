package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;
import de.nordakademie.nakjava.server.shared.proxy.actions.PlayAgainAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Determines and creates ActionContexts(PlayAgainAction) if current state of
 * Player is EndofGameState
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class EndOfGameRule extends StateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		actions.add(new PlayAgainAction(sessionId));
		return actions;
	}

	@Override
	public State getState() {
		return State.ENDOFGAMESTATE;
	}

}
