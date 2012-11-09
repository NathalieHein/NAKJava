package de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

public class EditDeckSpecificInformation implements StateSpecificInformation {
	private Map<CardInformation, Boolean> chosenCards;
	private String currentPartOfDeckName;

	public EditDeckSpecificInformation(
			Map<CardInformation, Boolean> chosenCards,
			String currentPartOfDeckName) {
		this.chosenCards = chosenCards;
		this.currentPartOfDeckName = currentPartOfDeckName;
	}

	public Map<CardInformation, Boolean> getChosenCards() {
		return chosenCards;
	}

	public String getCurrentPartOfDeckName() {
		return currentPartOfDeckName;
	}

}
