package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;

public class VisibleModelUpdater {

	// Suppresses default constructor for noninstantiability
	private VisibleModelUpdater() {
		throw new AssertionError();
	}

	private static void updatePlayerModel(Player player, PlayerState self,
			PlayerState opponent) {
		// TODO Do I want to see what card the opponent played???
		// TODO ErrorHandling: how are we doing it?
		// TODO this is only game itself not pregame: should be sufficient right
		// now for KI
		PlayerModel playerModel = player.getState().getModel();
		playerModel.setArtifacts(self.getArtifacts());
		List<CardInformation> cards = new ArrayList<>();
		for (String cardName : self.getCards().getCardsOnHand()) {
			CardInformation cardInfo = CardLibrary.get().getCardInformation()
					.get(cardName);
			if (cardInfo != null) {
				cards.add(cardInfo);
			} else {
				// throw new Exception("Card not found in CardLibrary");
			}
		}
		playerModel.setCardHand(cards);
		Map<Target, State> targetToState = new HashMap<>();
		targetToState.put(Target.SELF, self.getState());
		targetToState.put(Target.OPPONENT, opponent.getState());
		playerModel.setTargetToState(targetToState);
	}

	public static void update(Session session) {
		for (Player player : session.getSetOfPlayers()) {
			for (Player otherPlayer : session.getSetOfPlayers()) {
				if (player != otherPlayer) {
					updatePlayerModel(player, session.getPlayerToStateMap()
							.get(player),
							session.getPlayerToStateMap().get(otherPlayer));
				}
			}
		}
	}
}
