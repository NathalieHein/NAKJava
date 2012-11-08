package de.nordakademie.nakjava.server.internal.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditDeckSpecificModel implements StateSpecificModel {
	private Map<String, Boolean> chosenCards;
	private String currentPartOfDeckName = "";

	public EditDeckSpecificModel(List<String> allCards) {
		chosenCards = new HashMap<>();
		for (String cardName : allCards) {
			chosenCards.put(cardName, false);
		}
	}

	public void setCardTo(String card, boolean value) {
		chosenCards.put(card, value);
	}

	public Map<String, Boolean> getChosenCards() {
		return chosenCards;
	}

	public String getCurrentPartOfDeckName() {
		return currentPartOfDeckName;
	}

	public void appendPartOfDeckName(char character) {
		currentPartOfDeckName = currentPartOfDeckName + character;
	}
}
