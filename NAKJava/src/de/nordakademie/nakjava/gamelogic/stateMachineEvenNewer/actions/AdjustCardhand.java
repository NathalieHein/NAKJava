package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;

/**
 * adjusts the cardhand so that the player has numberOfCardsOnHand on his hand
 * afterwards
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class AdjustCardhand implements StateAction {
	private final int numberOfCardsOnHand = 6;

	@Override
	public void perform(Model model) {
		CardSet currentPlayersCards = ((InGameSpecificModel) model.getSelf()
				.getStateSpecificModel()).getCards();
		if (currentPlayersCards.getNumberOfCardsOnHand() < numberOfCardsOnHand) {
			currentPlayersCards.drawUntilNCardsOnHand(numberOfCardsOnHand);
			StateMachine.getInstance().run(model);
		} else if (currentPlayersCards.getNumberOfCardsOnHand() == numberOfCardsOnHand) {
			StateMachine.getInstance().run(model);
		}
	}

}
