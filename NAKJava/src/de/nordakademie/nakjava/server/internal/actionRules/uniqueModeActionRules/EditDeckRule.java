package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.Alphabet;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.DiscardDeckEditAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.RemoveDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SelectCardForDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.TypeDeckNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class EditDeckRule extends NonSimulationStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		EditDeckSpecificModel model = (EditDeckSpecificModel) getStateSpecificModel(
				sessionId, player);
		long batch = getBatch(sessionId);
		for (String card : model.getChosenCards().keySet()) {
			actions.add(new SelectCardForDeckAction(card, batch, sessionId));
		}
		for (char character : Alphabet.getLetters()) {
			actions.add(new TypeDeckNameAction(character, batch, sessionId));
		}
		actions.add(new RemoveDeckAction(batch, sessionId));
		actions.add(new DiscardDeckEditAction(batch, sessionId));
		return actions;

	}

	@Override
	public State getState() {
		return State.EDITDECK;
	}

}
