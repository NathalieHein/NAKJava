package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;

//TODO do we allow only strategies of type "win" or also of other types: e.g. HaveAlwaysNumberOfCardsOnHandStrategy
public interface WinStrategy {
	/**
	 * Returns the RoundResult for both players. The result is in itself
	 * consistent, which means that there are just 3 possibilities: Neutral -
	 * Neutral, Win - Loss, Loss - Win
	 * 
	 * @param playerMap
	 * @return
	 */
	public Map<Target, RoundResult> hasWon(Map<Target, PlayerState> playerMap);
}
