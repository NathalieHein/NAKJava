package de.nordakademie.nakjava.server.internal;

import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;

public class VisibleModelUpdater {

	// Suppresses default constructor for noninstantiability
	private VisibleModelUpdater() {
		throw new AssertionError();
	}

	private static void updatePlayerModel(Player player, long sessionId) {
		// TODO ErrorHandling: how are we doing it?
		PlayerModel playerModel = player.getState().getModel();
		Session session = Sessions.getInstance().getSession(sessionId);
		PlayerState self = session.getPlayerStateForPlayer(player);
		PlayerState opponent = session.getPlayerStateForPlayer(session
				.getOneOtherPlayer(player));
		// TODO create something like "ActionRules"
		// TODO insert StateSpecificInfos
		// TODO setting of dirtyBit via "ActionRules"
		/*
		 * playerModel.setArtifacts(self.getArtifacts()); List<CardInformation>
		 * cards = new ArrayList<>(); for (String cardName :
		 * self.getCards().getCardsOnHand()) { CardInformation cardInfo =
		 * CardLibrary.get().getCardInformation() .get(cardName); if (cardInfo
		 * != null) { cards.add(cardInfo); } else { // throw new
		 * Exception("Card not found in CardLibrary"); } }
		 * playerModel.setCardHand(cards);
		 */
		Map<Target, String> targetToName = new HashMap<>();
		targetToName.put(Target.SELF, player.getName());
		targetToName.put(Target.OPPONENT, session.getOneOtherPlayer(player)
				.getName());
		Map<Target, State> targetToState = new HashMap<>();
		targetToState.put(Target.SELF, self.getState());
		targetToState.put(Target.OPPONENT, opponent.getState());
		playerModel.setTargetToState(targetToState);
	}

	public static void update(long sessionId) {
		Session session = Sessions.getInstance().getSession(sessionId);
		for (Player player : session.getSetOfPlayers()) {
			updatePlayerModel(player, sessionId);

		}
	}
}
