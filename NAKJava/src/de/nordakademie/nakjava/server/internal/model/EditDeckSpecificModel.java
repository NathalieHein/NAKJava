package de.nordakademie.nakjava.server.internal.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class EditDeckSpecificModel implements StateSpecificModel {
	private Map<String, Boolean> chosenCards;
	private String currentPartOfDeckName = "";

	public EditDeckSpecificModel(Set<String> set) {
		chosenCards = new HashMap<>();
		for (String cardName : CardLibrary.get().getCardInformation().keySet()) {
			chosenCards.put(cardName, false);
		}
		if (set != null) {
			for (String cardName : set) {
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

	public static void main(String[] args) {
		ClasspathScanner.lookupAnnotatedScanners();
		EditDeckSpecificModel mod = new EditDeckSpecificModel(null);
		String card = "Ausbeutung";
		if (mod.getChosenCards().values().contains(true)) {
			System.out.println("something wron");
		}
		System.out.println("CardCount: " + mod.getChosenCards().size());
		System.out.println(card + " , " + mod.getChosenCards().get(card));
		mod.reverseSelectionOfCard(card);
		System.out.println(card + " , " + mod.getChosenCards().get(card));
		mod.reverseSelectionOfCard(card);
		System.out.println(card + " , " + mod.getChosenCards().get(card));
	}
}
