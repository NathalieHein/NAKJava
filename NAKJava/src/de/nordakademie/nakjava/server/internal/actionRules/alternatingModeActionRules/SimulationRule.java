package de.nordakademie.nakjava.server.internal.actionRules.alternatingModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.FinishSimulationAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Determines and creates FinishSimulationAction when State of player is
 * SimulationState
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class SimulationRule extends AlternatingStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		actions.add(new FinishSimulationAction(sessionId));
		return actions;
	}

	@Override
	public State getState() {
		return State.SIMULATIONSTATE;
	}

}
