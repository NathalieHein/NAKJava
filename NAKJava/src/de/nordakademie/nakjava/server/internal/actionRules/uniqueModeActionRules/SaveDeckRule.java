package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SaveDeckAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class SaveDeckRule extends NonSimulationStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		Session session = Sessions.getInstance().getSession(sessionId);
		long batch = session.getBatch().getCurrentBatchNr();
		actions.add(new SaveDeckAction(batch, sessionId));
		return actions;
	}

	@Override
	protected boolean isRuleApplicableImpl(Session session, Player player) {
		EditDeckSpecificModel model = (EditDeckSpecificModel) session
				.getPlayerStateForPlayer(player).getStateSpecificModel();
		String currentPartOfDeckName = model.getCurrentPartOfDeckName();
		for (String alreadyExistingDeckName : player.getSavedDecks().keySet()) {
			if (currentPartOfDeckName == alreadyExistingDeckName) {
				return false;
			}
		}
		return currentPartOfDeckName != null;
	}

	@Override
	public State getState() {
		return State.EDITDECK;
	}

}
