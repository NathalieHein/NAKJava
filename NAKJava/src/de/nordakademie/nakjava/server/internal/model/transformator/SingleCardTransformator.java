package de.nordakademie.nakjava.server.internal.model.transformator;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.server.internal.model.Transformator;

public class SingleCardTransformator implements
		Transformator<String, CardInformation> {

	@Override
	public CardInformation transform(String input) {
		return CardLibrary.get().getCardInformationForName(input);
	}

}
