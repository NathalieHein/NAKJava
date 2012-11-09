package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.Alphabet;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.TypePlayerNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class TypePlayerNameRule extends NonSimulationStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		for (char letter : Alphabet.getLetters()) {
			actions.add(new TypePlayerNameAction(letter, getBatch(sessionId),
					sessionId));
		}
		return actions;

	}

	@Override
	public State getState() {
		return State.LOGIN;
	}
}
