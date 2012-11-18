package de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.actionRules.StateRule;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.FinishConfiguringAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class FinishConfiguringGameRule extends StateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		actions.add(new FinishConfiguringAction(sessionId));
		return actions;
	}

	@Override
	public State getState() {
		return State.CONFIGUREGAME;
	}

	@Override
	protected boolean isRuleApplicableImpl(long sessionId, Player player) {
		ConfigureGameSpecificModel specificModel = (ConfigureGameSpecificModel) getPlayerState(
				sessionId, player).getStateSpecificModel();
		return specificModel.getChosenDeck() != null;
	}

}
