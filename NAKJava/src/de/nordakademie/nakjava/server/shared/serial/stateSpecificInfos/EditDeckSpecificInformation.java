package de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos;

import java.util.Map;

public class EditDeckSpecificInformation implements StateSpecificInformation {
	private Map<String, Boolean> chosenCards;
	private String currentPartOfDeckName;

	public EditDeckSpecificInformation(Map<String, Boolean> chosenCards,
			String currentPartOfDeckName) {
		this.chosenCards = chosenCards;
		this.currentPartOfDeckName = currentPartOfDeckName;
	}

	public Map<String, Boolean> getChosenCards() {
		return chosenCards;
	}

	public String getCurrentPartOfDeckName() {
		return currentPartOfDeckName;
	}

}
