package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.Alphabet;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.TypeDeckNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class TypeSpecialCharactersForDeck extends StateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		for (char key : Alphabet.getNotAtTheBeginningLetters()) {
			actions.add(new TypeDeckNameAction(key, sessionId));
		}
		return actions;
	}

	@Override
	public State getState() {
		return State.EDITDECK;
	}

}
