package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Players;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;
import de.nordakademie.nakjava.server.internal.model.LoginSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.SubmitPlayerNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class FinishTypingPlayerNameRule extends StateRule {

	@Override
	protected boolean isRuleApplicableImpl(long sessionId, Player player) {
		String currentPartOfName = ((LoginSpecificModel) getStateSpecificModel(
				sessionId, player)).getCurrentPartOfName();
		if (!Players.getInstance().addPlayerName(currentPartOfName)) {
			return false;
		}
		return !currentPartOfName.equals("");
	}

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		actions.add(new SubmitPlayerNameAction(sessionId));
		return actions;
	}

	@Override
	public State getState() {
		return State.LOGIN;
	}
}
