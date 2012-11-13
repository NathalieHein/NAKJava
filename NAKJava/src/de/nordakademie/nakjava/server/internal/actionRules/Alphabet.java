package de.nordakademie.nakjava.server.internal.actionRules;

public abstract class Alphabet {
	// '/n' means space
	// '/b' means backspace
	private static final char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };

	private static final char[] notAtTheBeginningLetters = { '\b', ' ' };

	public static char[] getLetters() {
		return letters;
	}

	public static char[] getNotAtTheBeginningLetters() {
		return notAtTheBeginningLetters;
	}
}
