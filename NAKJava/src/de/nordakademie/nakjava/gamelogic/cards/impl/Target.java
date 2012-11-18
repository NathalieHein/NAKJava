package de.nordakademie.nakjava.gamelogic.cards.impl;

/**
 * Enumeration for targets. The targets are view dependant and may be changed
 * during game.
 * 
 */
public enum Target {

	SELF("du", "dich"), OPPONENT("dein Gegner", "Gegner");

	private String nomDescription;
	private String datDescription;

	private Target(String nominativDescription, String dativDescription) {
		this.nomDescription = nominativDescription;
		this.datDescription = dativDescription;
	}

	public String getAkkusativDescription() {
		return datDescription;
	}

	public String getNominativDescription() {
		return nomDescription;
	}
}
