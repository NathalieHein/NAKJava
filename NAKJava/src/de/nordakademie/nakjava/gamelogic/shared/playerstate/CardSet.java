package de.nordakademie.nakjava.gamelogic.shared.playerstate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardSet implements Serializable {

	// TODO questionable return-types: boolean (operation worked) or
	// List<>(performed for those cards) or void
	private List<String> deck = new ArrayList<>();
	private List<String> hand = new ArrayList<>();
	private List<String> cemetry = new ArrayList<>();

	public void shuffle() {
		deck.addAll(cemetry);
	}

	public CardSet() {

	}

	public CardSet(List<String> cards) {
		deck.addAll(cards);
	}

	public void addOneCard(String card) {
		deck.add(card);
	}

	public void addAllCards(List<String> cards) {
		deck.addAll(cards);
	}

	public boolean discardCardFromHand(String card) {
		if (hand.remove(card)) {
			cemetry.add(card);
			return true;
		}
		return false;
	}

	public String drawCardFromDeck() {
		if (deck.size() != 0) {
			String card = deck.remove((int) (Math.random() * deck.size()));
			hand.add(card);
			return card;
		} else if (getCardSetSize() > hand.size()) {
			shuffle();
			return drawCardFromDeck();
		} else {
			return null;

		}
	}

	public boolean drawUntilNCardsOnHand(int n) {
		if (n <= getCardSetSize()) {
			int numberOfDrawnCards = 0;
			while (numberOfDrawnCards < n) {
				drawCardFromDeck();
				numberOfDrawnCards++;
			}
			return true;
		}
		return false;
	}

	public List<String> drawNCardsFromDeck(int n) {
		List<String> list = new ArrayList<>();
		int i = 1;
		String card = drawCardFromDeck();
		if (card != null) {
			list.add(card);
		}
		while (i < n && card != null) {
			drawCardFromDeck();
			list.add(card);
			i++;
		}
		return list;
	}

	public void copyToFile(String address) {
		deck.addAll(hand);
		shuffle();
		// TODO copy to file
	}

	private int getCardSetSize() {
		return hand.size() + deck.size() + cemetry.size();
	}
}
