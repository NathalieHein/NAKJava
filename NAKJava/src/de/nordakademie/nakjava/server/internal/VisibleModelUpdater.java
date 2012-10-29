package de.nordakademie.nakjava.server.internal;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;

public class VisibleModelUpdater {

	// Suppresses default constructor for noninstantiability
	private VisibleModelUpdater() {
		throw new AssertionError();
	}

	private static void updatePlayerModel(Player player, PlayerState opponent) {
		// TODO Do I want to see what card the opponent played???
		// TODO ErrorHandling: how are we doing it?
		// TODO state differently -> as Enums
		PlayerModel playerModel = player.getState().getModel();
		playerModel.setArtifacts(player.getGamelogicPlayer().getArtifacts());
		List<CardInformation> cards = new ArrayList<>();
		for (String cardName : player.getGamelogicPlayer().getCards()
				.getCardsOnHand()) {
			CardInformation cardInfo = CardLibrary.get().getCardInformation()
					.get(cardName);
			if (cardInfo != null) {
				cards.add(cardInfo);
			} else {
				// throw new Exception("Card not found in CardLibrary");
			}
		}
		playerModel.setCardHand(cards);
		playerModel.setSelfState(player.getGamelogicPlayer().getState());
		playerModel.setOpponentState(opponent.getState());
	}

	public static void update(Model model, long batchNr) {
		if (!model.isModeUnique()) {
			for (Player player : model.getIterableListOfPlayers()) {
				for (Player otherPlayer : model.getIterableListOfPlayers()) {
					if (player != otherPlayer) {
						updatePlayerModel(player,
								otherPlayer.getGamelogicPlayer());
					}
				}
			}
		} else {
			// TODO in CardChooseState that player that last triggered action
			updatePlayerModel(model.getActionInvoker(), null);
		}
	}
}
