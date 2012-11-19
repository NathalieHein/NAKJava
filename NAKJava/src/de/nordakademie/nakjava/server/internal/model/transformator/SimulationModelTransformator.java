package de.nordakademie.nakjava.server.internal.model.transformator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.SimulationModel;
import de.nordakademie.nakjava.server.internal.model.Transformator;
import de.nordakademie.nakjava.server.shared.serial.VisibleSimulationModel;

/**
 * Transforms a list of SimulationModel into a list of VisibleSimulationModels
 * that contains less information
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class SimulationModelTransformator implements
		Transformator<List<SimulationModel>, List<VisibleSimulationModel>> {

	@Override
	public List<VisibleSimulationModel> transform(List<SimulationModel> input) {
		List<VisibleSimulationModel> visibleModels = new LinkedList<>();
		for (SimulationModel simulationModel : input) {
			Map<Target, List<? extends Artifact>> targetToArtifact = new HashMap<>();
			InGameSpecificModel selfInGameSimulation = (InGameSpecificModel) simulationModel
					.getSelf().getStateSpecificModel();
			targetToArtifact.put(Target.SELF,
					selfInGameSimulation.getArtifacts());
			InGameSpecificModel opponentInGameSimulation = (InGameSpecificModel) simulationModel
					.getOpponent().getStateSpecificModel();
			targetToArtifact.put(Target.OPPONENT,
					opponentInGameSimulation.getArtifacts());
			visibleModels.add(new VisibleSimulationModel(simulationModel
					.getSimulatedNumberOfRounds(), simulationModel
					.getSimulatedCard(), targetToArtifact));
		}
		return visibleModels;
	}
}
