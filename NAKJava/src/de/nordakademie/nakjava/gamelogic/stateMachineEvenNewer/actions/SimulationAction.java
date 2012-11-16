package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.server.internal.model.Model;

public class SimulationAction implements StateAction {

	@Override
	public void perform(Model model) {
		model.getSelf().setSimulationModels(null);
	}

}
