package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

/**
 * The {@link CardLibrary} is a singleton that allows lookups for both:
 * card-metadata ({@link CardInformation}) and card implementations (types that
 * extend {@link AbstractCard})
 * 
 */
public class CardLibrary {

	private Map<String, AbstractCard> cards;
	private Map<String, CardInformation> cardInformation;

	private static CardLibrary instance;

	/**
	 * This singleton is instantiated by {@link CardGenerator}
	 */
	CardLibrary() {
		cards = new HashMap<>();
		cardInformation = new HashMap<>();
		CardLibrary.instance = this;
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

	public CardInformation getCardInformationForName(String cardName) {
		return cardInformation.get(cardName);
	}

	public AbstractCard getCardForName(String cardName) {
		return cards.get(cardName);
	}

}
