package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;
import de.nordakademie.nakjava.server.internal.model.LoginSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.SubmitPlayerNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class FinishTypingPlayerNameRule extends NonSimulationStateRule {

	@Override
	protected boolean isRuleApplicableImpl(Session session, Player player) {
		LoginSpecificModel model = (LoginSpecificModel) session
				.getPlayerStateForPlayer(player).getStateSpecificModel();
		return model.getCurrentPartOfName() != null;
	}

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		actions.add(new SubmitPlayerNameAction(Sessions.getInstance()
				.getSession(sessionId).getBatch().getCurrentBatchNr(),
				sessionId));
		return actions;
	}

	@Override
	public State getState() {
		return State.LOGIN;
	}
}
