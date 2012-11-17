package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.SimulationModel;

public class SimulateCardRunnable implements Runnable {
	SimulationModel simulationModel;

	public SimulateCardRunnable(SimulationModel simulationModel) {
		this.simulationModel = simulationModel;
	}

	@Override
	public void run() {
		PlayerState simulationSelf = simulationModel.getSelf();
		PlayerState simulationOponent = simulationModel.getOpponent();
		PlayCardAuxiliary.playCard(simulationModel,
				simulationModel.getSimulatedCard());
		do {
			PlayCardAuxiliary.checkEndOfGame(simulationModel);
			if (simulationSelf.getState() == State.ADJUSTCARDHANDSTATE) {
				((InGameSpecificModel) simulationSelf.getStateSpecificModel())
						.getCards().discardRandomCardFromHand();
				StateMachine.getInstance().run(simulationModel);
			}
			if (simulationOponent.getState() == State.ADJUSTCARDHANDSTATE) {
				((InGameSpecificModel) simulationOponent
						.getStateSpecificModel()).getCards()
						.discardRandomCardFromHand();
				StateMachine.getInstance().run(simulationModel);
			}
			if (simulationSelf.getState() == State.PLAYCARDSTATE) {
				simulationModel.incrementCountRounds();
			}
		} while (!simulationModel.isSimulatingFinished()
				&& simulationSelf.getState() != State.ENDOFGAMESTATE);
	}

}