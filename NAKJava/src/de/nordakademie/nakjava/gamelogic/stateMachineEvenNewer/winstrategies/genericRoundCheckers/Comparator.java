package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers;

public enum Comparator {
	EQUAL("gleich viel"), GREATER("mehr als"), SMALLER("weniger als");

	private String description;

	private Comparator(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
