package de.nordakademie.nakjava.server.internal.model;

import java.util.Set;

public class ConfigureGameSpecificModel implements StateSpecificModel {
	private Set<String> chosenDeck;
	private String winStrategy;

	public Set<String> getChosenDeck() {
		return chosenDeck;
	}

	public void setChosenDeck(Set<String> chosenDeck) {
		this.chosenDeck = chosenDeck;
	}

	public String getWinStrategy() {
		return winStrategy;
	}

	public void setWinStrategy(String winStrategy) {
		this.winStrategy = winStrategy;
	}

}
