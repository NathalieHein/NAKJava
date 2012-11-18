package de.nordakademie.nakjava.gamelogic.cards.zauberlabor;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Pure Magie",
		type = CardType.ZAUBERLABOR,
		costs = { @Cost(amount = 15,
				ressource = Kristalle.class) },
		additionalDescription = "Verwirf alle Karten, ziehe 6 zufällige Zauberlabor-Karten")
public class PureMagie extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		CardSet selfCards = ((InGameSpecificModel) states.get(Target.SELF)
				.getStateSpecificModel()).getCards();
		selfCards.discardAllCardsFromHand();
		int count = 0;
		int passedCards = 0;
		String drawnCard;
		while (count < 6 && passedCards < selfCards.getCardSetSize()) {
			drawnCard = selfCards.drawCardFromDeck();
			passedCards++;
			if (CardLibrary.get().getCardInformationForName(drawnCard)
					.getType() == CardType.ZAUBERLABOR) {
				count++;
			} else {
				selfCards.discardCardFromHand(drawnCard);
			}
		}

	}

}
