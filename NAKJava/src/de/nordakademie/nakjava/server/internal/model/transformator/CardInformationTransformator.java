package de.nordakademie.nakjava.server.internal.model.transformator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.server.internal.model.Transformator;

public class CardInformationTransformator implements
		Transformator<Map<String, Boolean>, Map<CardInformation, Boolean>> {

	@Override
	public Map<CardInformation, Boolean> transform(Map<String, Boolean> input) {
		Map<CardInformation, Boolean> map = new HashMap<>();
		CardLibrary cardLibrary = CardLibrary.get();
		for (Entry<String, Boolean> cardEntry : input.entrySet()) {
			map.put(cardLibrary.getCardInformationForName(cardEntry.getKey()),
					cardEntry.getValue());
		}
		return map;
	}

}
