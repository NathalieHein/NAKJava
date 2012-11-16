package de.nordakademie.nakjava.server.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimulationModels implements Serializable {
	// TODO do you go through lists as well???
	// @VisibleField(targets = { @TargetInState(states = { State.SIMULATIONSTATE
	// }, target = Target.SELF) })
	private List<SimulationModel> simulationModels;

	public SimulationModels(Model model, List<String> cards,
			int[] simulationThresholds) {
		simulationModels = new ArrayList<>();
		for (String card : cards) {
			for (int i : simulationThresholds) {
				try {
					simulationModels.add(new SimulationModel(model, card, i));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public List<SimulationModel> getSimulationModels() {
		return simulationModels;
	}
}
