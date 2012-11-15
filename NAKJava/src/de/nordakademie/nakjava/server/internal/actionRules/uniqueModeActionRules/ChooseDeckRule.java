package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.CreateNewDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.EditDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectStandardDeckAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ChooseDeckRule extends NonSimulationStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		PlayerState self = getSession(sessionId)
				.getPlayerStateForPlayer(player);
		for (Deck deck : self.getSavedDecks()) {
			actions.add(new EditDeckAction(deck.getName(), sessionId));
			actions.add(new SelectDeckAction(deck.getName(), sessionId));
		}
		actions.add(new SelectStandardDeckAction("StandardDeck", sessionId));
		actions.add(new CreateNewDeckAction(sessionId));
		return actions;
	}

	@Override
	public State getState() {
		return State.CONFIGUREGAME;
	}

}
