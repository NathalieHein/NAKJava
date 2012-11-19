package de.nordakademie.nakjava.server.internal.model.transformator;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.CardSet;
import de.nordakademie.nakjava.server.internal.model.Transformator;

/**
 * transforms a CardSet into a list of CardInformation-objects
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class CardTransformator implements
		Transformator<CardSet, List<CardInformation>> {

	@Override
	public List<CardInformation> transform(CardSet input) {
		List<CardInformation> cardsInHand = new ArrayList<>();
		for (String cardName : input.getCardsOnHand()) {
			cardsInHand.add(CardLibrary.get().getCardInformationForName(
					cardName));
		}
		return cardsInHand;
	}

}
