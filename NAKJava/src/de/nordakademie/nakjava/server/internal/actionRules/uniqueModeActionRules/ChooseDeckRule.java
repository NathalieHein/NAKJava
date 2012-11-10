package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.NonSimulationStateRule;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.CreateNewDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.EditDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectStandardDeckAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ChooseDeckRule extends NonSimulationStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		long batch = getBatch(sessionId);
		for (String savedDeckName : player.getSavedDecks().keySet()) {
			actions.add(new EditDeckAction(savedDeckName, batch, sessionId));
			actions.add(new SelectDeckAction(savedDeckName, batch, sessionId));
		}
		actions.add(new SelectStandardDeckAction("StandardDeck", batch,
				sessionId));
		actions.add(new CreateNewDeckAction(batch, sessionId));
		return actions;
	}

	@Override
	public State getState() {
		return State.CONFIGUREGAME;
	}

}
