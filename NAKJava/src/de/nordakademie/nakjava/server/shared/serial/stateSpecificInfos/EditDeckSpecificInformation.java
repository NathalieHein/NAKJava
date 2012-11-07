package de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos;

import java.util.Map;

public class EditDeckSpecificInformation implements StateSpecificInformation {
	private Map<String, Boolean> chosenCards;

	public EditDeckSpecificInformation(Map<String, Boolean> chosenCards) {
		this.chosenCards = chosenCards;
	}

	public Map<String, Boolean> getChosenCards() {
		return chosenCards;
	}

}
