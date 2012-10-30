package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

public enum RoundResult {
	LOST, WIN, NEUTRAL;

	public static RoundResult invert(RoundResult result) {
		if (result == LOST) {
			return WIN;
		}
		if (result == WIN) {
			return LOST;
		}
		return NEUTRAL;
	}
}
