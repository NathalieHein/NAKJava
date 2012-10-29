package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;

public class AdjustCardhand {

	public static void perform(Map<Target, PlayerState> playerStateMap) {
		// TODO Auto-generated method stub
		// TODO put number 6 in model somewhere, as constant or enum
		CardSet currentPlayersCards = playerStateMap.get(Target.SELF)
				.getCards();
		if (currentPlayersCards.getNumberOfCardsOnHand() < 6) {
			currentPlayersCards.drawUntilNCardsOnHand(6);
			StateMachine.run(playerStateMap);
		} else if (currentPlayersCards.getNumberOfCardsOnHand() == 6) {
			StateMachine.run(playerStateMap);
		}
	}

}
