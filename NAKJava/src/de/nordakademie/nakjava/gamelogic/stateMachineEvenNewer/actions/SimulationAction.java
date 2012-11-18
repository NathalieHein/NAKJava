package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.server.internal.model.Model;

/**
 * At the end of simulation, this SimulationAction resets the simulationModels
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class SimulationAction implements StateAction {

	@Override
	public void perform(Model model) {
		model.getSelf().setSimulationModels(null);
	}

}
