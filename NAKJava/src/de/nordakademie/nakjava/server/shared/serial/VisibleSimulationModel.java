package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;
import de.nordakademie.nakjava.server.internal.model.transformator.SingleCardTransformator;

/**
 * contains to be serialized information to client that contains result(List of
 * Artifacts) of one simulation (simulatedCard and toBeSimulatedNumberOfRounds)
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class VisibleSimulationModel implements Serializable {
	@VisibleField(targets = { @TargetInState(states = { State.SIMULATIONSTATE },
			target = Target.SELF) })
	private int toBeSimulatedNumberOfRounds;
	@VisibleField(targets = { @TargetInState(states = { State.SIMULATIONSTATE },
			target = Target.SELF) },
			transformer = SingleCardTransformator.class)
	private String simulatedCard;
	private Map<Target, List<? extends Artifact>> targetToArtifacts;

	public VisibleSimulationModel(int toBeSimulatedNumberOfRounds,
			String simulatedCard,
			Map<Target, List<? extends Artifact>> targetToArtifacts) {
		super();
		this.toBeSimulatedNumberOfRounds = toBeSimulatedNumberOfRounds;
		this.simulatedCard = simulatedCard;
		this.targetToArtifacts = targetToArtifacts;
	}

	public int getToBeSimulatedNumberOfRounds() {
		return toBeSimulatedNumberOfRounds;
	}

	public String getSimulatedCard() {
		return simulatedCard;
	}

	public Map<Target, List<? extends Artifact>> getTargetToArtifacts() {
		return targetToArtifacts;
	}

}
