package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

public interface WinCheck {

	/**
	 * Get the RoundResult for the current player with this criteria.
	 * 
	 * @param states
	 * @return
	 */
	public RoundResult check(Map<Target, PlayerState> states);
}
