package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

public class CardLibrary {

	private Map<String, AbstractCard> cards;
	private Map<String, CardInformation> cardInformation;

	private static CardLibrary instance;

	CardLibrary() {
		cards = new HashMap<>();
		cardInformation = new HashMap<>();
		instance = this;
	}

	public static CardLibrary get() {
		return instance;
	}

	public Map<String, AbstractCard> getCards() {
		return cards;
	}

	public Map<String, CardInformation> getCardInformation() {
		return cardInformation;
	}

}
