package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;

public class AdjustCardhand implements StateAction {

	@Override
	public void perform(Model model) {
		// TODO Auto-generated method stub
		// TODO put number 6 in model somewhere, as constant or enum
		CardSet currentPlayersCards = ((InGameSpecificModel) model.getSelf()
				.getStateSpecificModel()).getCards();
		if (currentPlayersCards.getNumberOfCardsOnHand() < 6) {
			currentPlayersCards.drawUntilNCardsOnHand(6);
			StateMachine.getInstance().run(model);
		} else if (currentPlayersCards.getNumberOfCardsOnHand() == 6) {
			StateMachine.getInstance().run(model);
		}
	}

}
