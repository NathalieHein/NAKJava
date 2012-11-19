package de.nordakademie.nakjava.server.internal.model.transformator;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.server.internal.model.Transformator;

/**
 * transforms a cardName into its corresponding CardInformation-object
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class SingleCardTransformator implements
		Transformator<String, CardInformation> {

	@Override
	public CardInformation transform(String input) {
		return CardLibrary.get().getCardInformationForName(input);
	}

}
