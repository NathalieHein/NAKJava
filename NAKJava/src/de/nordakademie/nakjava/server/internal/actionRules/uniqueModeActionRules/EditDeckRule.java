package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.Alphabet;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.DiscardDeckEditAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.RemoveDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SelectCardForDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.TypeDeckNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * determines and creates ActionContexts(SelectCardForDeckAction,
 * TypeDeckNameAction, RemoveDeckAction, DiscardDeckAction) if current State of
 * Player is Editdeck
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class EditDeckRule extends StateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		EditDeckSpecificModel model = (EditDeckSpecificModel) getStateSpecificModel(
				sessionId, player);
		for (String card : model.getChosenCards().keySet()) {
			actions.add(new SelectCardForDeckAction(card, sessionId));
		}
		for (char character : Alphabet.getLetters()) {
			actions.add(new TypeDeckNameAction(character, sessionId));
		}
		actions.add(new RemoveDeckAction(sessionId));
		actions.add(new DiscardDeckEditAction(sessionId));
		return actions;

	}

	@Override
	public State getState() {
		return State.EDITDECK;
	}

}
