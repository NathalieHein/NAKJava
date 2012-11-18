package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifacts;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;

/**
 * sets up InGameSpecificModel at start of game + checks whether both players
 * are already in that state -> if yes, then game begins
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class ReadyToStartAction implements StateAction {

	@Override
	public void perform(Model model) {
		PlayerState self = model.getSelf();
		PlayerState opponent = model.getOpponent();
		ConfigureGameSpecificModel configureSpecificModel = (ConfigureGameSpecificModel) self
				.getStateSpecificModel();
		InGameSpecificModel gameSpecificModel = new InGameSpecificModel(
				Artifacts.getInstance().getInitialArtifacts());
		gameSpecificModel.setCards(configureSpecificModel.getChosenDeck()
				.getCards());
		gameSpecificModel.getCards().drawUntilNCardsOnHand(6);
		self.setStateSpecificModel(gameSpecificModel);
		if (opponent != null && opponent.getState() == State.READYTOSTARTSTATE) {
			opponent.setState(State.STOP);
			StateMachine.getInstance().run(model);
		}
	}
}
