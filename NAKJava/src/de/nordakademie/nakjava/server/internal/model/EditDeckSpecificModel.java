package de.nordakademie.nakjava.server.internal.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;
import de.nordakademie.nakjava.server.internal.model.transformator.CardInformationTransformator;
import de.nordakademie.nakjava.server.persistence.Deck;

public class EditDeckSpecificModel implements StateSpecificModel {
	@VisibleField(targets = { @TargetInState(states = { State.EDITDECK },
			target = Target.SELF) },
			transformer = CardInformationTransformator.class)
	private Map<String, Boolean> chosenCards;
	@VisibleField(targets = { @TargetInState(states = { State.EDITDECK },
			target = Target.SELF) })
	private String currentPartOfDeckName = "";
	private Deck deck;

	public EditDeckSpecificModel(Deck deck) {
		this.deck = deck;
		Set<String> cardNames = deck.getCards();
		chosenCards = new HashMap<>();
		for (String cardName : CardLibrary.get().getCardInformation().keySet()) {
			chosenCards.put(cardName, false);
		}
		if (cardNames != null) {
			for (String cardName : cardNames) {
				chosenCards.put(cardName, true);
			}
		}
	}

	public void reverseSelectionOfCard(String card) {
		chosenCards.put(card, !chosenCards.get(card));
	}

	public Map<String, Boolean> getChosenCards() {
		return chosenCards;
	}

	public String getCurrentPartOfDeckName() {
		return currentPartOfDeckName;
	}

	public void appendPartOfDeckName(char character) {
		if (character != '\b') {
			currentPartOfDeckName = currentPartOfDeckName + character;
		} else {
			currentPartOfDeckName = currentPartOfDeckName.substring(0,
					currentPartOfDeckName.length() - 1);
		}
	}

	public int getNumberOfSelectedCards() {
		int count = 0;
		for (Boolean cardSelected : chosenCards.values()) {
			if (cardSelected) {
				count++;
			}
		}
		return count;
	}

	public Deck getDeck() {
		return deck;
	}
}
