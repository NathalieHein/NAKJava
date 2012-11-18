package de.nordakademie.nakjava.server.internal.model;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;
import de.nordakademie.nakjava.server.internal.model.transformator.SingleCardTransformator;
import de.nordakademie.nakjava.util.DeepCopyUtility;

/**
 * Model to process Simulation via StateMachine; exists and processed once for
 * each card and numberOfSimulated rounds
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class SimulationModel extends Model {
	@VisibleField(targets = { @TargetInState(states = { State.SIMULATIONSTATE },
			target = Target.SELF) })
	private int toBeSimulatedNumberOfRounds;
	private int countRounds;
	@VisibleField(targets = { @TargetInState(states = { State.SIMULATIONSTATE },
			target = Target.SELF) },
			transformer = SingleCardTransformator.class)
	private String simulatedCard;

	private SimulationModel(PlayerState self, PlayerState opponent,
			String winStrategy) {
		super(self);
		this.opponent = opponent;
		this.strategy = winStrategy;
	}

	public int getSimulatedNumberOfRounds() {
		return toBeSimulatedNumberOfRounds;
	}

	public boolean isSimulatingFinished() {
		return toBeSimulatedNumberOfRounds == countRounds;
	}

	public void incrementCountRounds() {
		countRounds++;
	}

	/**
	 * 
	 * @param model
	 *            : model to be copied for simulation
	 * @param simulatedCard
	 * @param toBeSimulatedNumberOfRounds
	 */
	public SimulationModel(Model model, String simulatedCard,
			int toBeSimulatedNumberOfRounds) {
		this(DeepCopyUtility.deepCopy(model.getSelf()), DeepCopyUtility
				.deepCopy(model.getOpponent()), model.getStrategy());
		this.simulatedCard = simulatedCard;
		this.toBeSimulatedNumberOfRounds = toBeSimulatedNumberOfRounds;
		this.countRounds = 0;
	}

	public String getSimulatedCard() {
		return simulatedCard;
	}

}
