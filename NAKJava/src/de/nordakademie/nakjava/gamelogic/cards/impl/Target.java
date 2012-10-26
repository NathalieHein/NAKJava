package de.nordakademie.nakjava.gamelogic.cards.impl;

public enum Target {

	SELF("dich"), OPPONENT("Gegner");

	private String description;

	private Target(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}
}
