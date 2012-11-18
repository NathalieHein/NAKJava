package de.nordakademie.nakjava.server.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;
import de.nordakademie.nakjava.server.internal.model.transformator.SimulationModelTransformator;

/**
 * List of SimulationModels
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class SimulationModels implements Serializable {
	@VisibleField(targets = { @TargetInState(states = { State.SIMULATIONSTATE },
			target = Target.SELF) },
			transformer = SimulationModelTransformator.class)
	private List<SimulationModel> simulationModels;

	public SimulationModels(Model model, List<String> cards,
			int[] simulationThresholds) {
		simulationModels = new ArrayList<>();
		for (String card : cards) {
			for (int i : simulationThresholds) {
				simulationModels.add(new SimulationModel(model, card, i));
			}
		}
	}

	public List<SimulationModel> getSimulationModels() {
		return simulationModels;
	}
}
