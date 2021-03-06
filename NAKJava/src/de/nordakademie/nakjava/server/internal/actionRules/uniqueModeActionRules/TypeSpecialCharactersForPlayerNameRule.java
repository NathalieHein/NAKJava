package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.Alphabet;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;
import de.nordakademie.nakjava.server.internal.model.LoginSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.TypePlayerNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * determines and creates TypePlayerNameaction if current state of player is
 * Login
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class TypeSpecialCharactersForPlayerNameRule extends StateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		for (char specialCharacter : Alphabet.getNotAtTheBeginningLetters()) {
			actions.add(new TypePlayerNameAction(specialCharacter, sessionId));
		}
		return actions;
	}

	@Override
	public State getState() {
		return State.LOGIN;
	}

	@Override
	protected boolean isRuleApplicableImpl(long sessionId, Player player) {
		return !((LoginSpecificModel) getStateSpecificModel(sessionId, player))
				.getCurrentPartOfName().equals("");
	}

}
