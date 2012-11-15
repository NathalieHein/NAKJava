package de.nordakademie.nakjava.gamelogic.cards.impl;

public enum Target {

	SELF("du", "dich"), OPPONENT("dein Gegner", "Gegner");

	private String nominativDescription;
	private String dativDescription;

	private Target(String nominativDescription, String dativDescription) {
		this.nominativDescription = nominativDescription;
		this.dativDescription = dativDescription;
	}

	public String getAkkusativDescription() {
		return dativDescription;
	}

	public String getNominativDescription() {
		return nominativDescription;
	}
}
