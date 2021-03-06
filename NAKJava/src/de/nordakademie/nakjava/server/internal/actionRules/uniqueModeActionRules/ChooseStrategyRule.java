package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectWinStrategy;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Determines and creates ActionContexts(SelectWinStrategy) when applicable
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class ChooseStrategyRule extends StateRule {

	@Override
	protected boolean isRuleApplicableImpl(long sessionId, Player player) {
		PlayerState opponent = getOtherPlayerState(sessionId, player);
		return opponent == null
				|| opponent.getState() != State.READYTOSTARTSTATE;
	}

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		for (String winstrategy : WinStrategies.getInstance().getStrategies()) {
			actions.add(new SelectWinStrategy(winstrategy, sessionId));
		}
		return actions;
	}

	@Override
	public State getState() {
		return State.CONFIGUREGAME;
	}

}
