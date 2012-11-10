package de.nordakademie.nakjava.server.internal.model;

public class LoginSpecificModel implements StateSpecificModel {
	private String currentPartOfName = "";

	public String getCurrentPartOfName() {
		return currentPartOfName;
	}

	public void appendPartOfName(char character) {
		if (character != '\b') {
			currentPartOfName = currentPartOfName + character;
		} else {
			currentPartOfName = currentPartOfName.substring(0,
					currentPartOfName.length() - 1);
		}
	}
}
