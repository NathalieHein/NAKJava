package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectWinStrategy;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ChooseStrategyRule extends NonSimulationStateRule {

	@Override
	protected boolean isRuleApplicableImpl(Session session, Player player) {
		return session.getModel().getStrategy() != null;
	}

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		long batch = Sessions.getInstance().getSession(sessionId).getBatch()
				.getCurrentBatchNr();
		for (String winstrategy : WinStrategies.getInstance().getStrategies()) {
			actions.add(new SelectWinStrategy(winstrategy, batch, sessionId));
		}
		return actions;
	}

	@Override
	public State getState() {
		return State.CONFIGUREGAME;
	}

}
