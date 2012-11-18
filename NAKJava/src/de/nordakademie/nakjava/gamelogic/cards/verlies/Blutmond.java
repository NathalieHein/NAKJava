package de.nordakademie.nakjava.gamelogic.cards.verlies;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.ArtifactEffect;
import de.nordakademie.nakjava.gamelogic.cards.impl.Card;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Cost;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Zauberlabor;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

@Card(name = "Blutmond",
		type = CardType.VERLIES,
		costs = { @Cost(amount = 8,
				ressource = Monster.class) },
		artifactEffects = { @ArtifactEffect(artifact = Zauberlabor.class,
				count = -1,
				target = Target.SELF) },
		additionalDescription = "Verwirf alle Karten, ziehe 8 zufallige Verlies-Karten.")
public class Blutmond extends AbstractCard {

	@Override
	protected void performAction(Map<Target, PlayerState> states) {
		super.performAction(states);
		CardSet selfCards = ((InGameSpecificModel) states.get(Target.SELF)
				.getStateSpecificModel()).getCards();
		selfCards.discardAllCardsFromHand();
		int count = 0;
		int passedCards = 0;
		String drawnCard;
		while (count < 8 && passedCards < selfCards.getCardSetSize()) {
			drawnCard = selfCards.drawCardFromDeck();
			passedCards++;
			if (CardLibrary.get().getCardInformationForName(drawnCard)
					.getType() == CardType.VERLIES) {
				count++;
			} else {
				selfCards.discardCardFromHand(drawnCard);
			}
		}
	}

}
