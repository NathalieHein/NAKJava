package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

/**
 * Checks whether a player has won. Many Checks form a winstrategy, e.g. Kill
 * the other player or collect more than x ressources.
 * 
 * @author Kai
 * 
 */
public interface WinCheck {

	/**
	 * Get the RoundResult for the current player with this criteria.
	 * 
	 * @param states
	 * @return
	 */
	public RoundResult check(Map<Target, PlayerState> states);

	public String getDescription();
}
