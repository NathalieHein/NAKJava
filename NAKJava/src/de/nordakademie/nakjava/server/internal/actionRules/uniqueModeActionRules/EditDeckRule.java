package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.DeselectCardForDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.DiscardDeckEditAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SelectCardForDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.TypeDeckNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class EditDeckRule extends NonSimulationStateRule {

	private static final char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '\n', '\b' };

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		Session session = Sessions.getInstance().getSession(sessionId);
		EditDeckSpecificModel model = (EditDeckSpecificModel) session
				.getPlayerStateForPlayer(player).getStateSpecificModel();
		long batch = session.getBatch().getCurrentBatchNr();
		for (Entry<String, Boolean> entry : model.getChosenCards().entrySet()) {
			if (entry.getValue() == true) {
				actions.add(new DeselectCardForDeckAction(entry.getKey(),
						batch, sessionId));
			} else {
				actions.add(new SelectCardForDeckAction(entry.getKey(), batch,
						sessionId));
			}
		}
		for (char character : letters) {
			actions.add(new TypeDeckNameAction(character, batch, sessionId));
		}
		actions.add(new DiscardDeckEditAction(batch, sessionId));
		return actions;

	}

	@Override
	public State getState() {
		return State.EDITDECK;
	}

}
