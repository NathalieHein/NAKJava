package de.nordakademie.nakjava.gamelogic.stateMachine;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

//TODO do we allow only strategies of type "win" or also of other types: e.g. HaveAlwaysNumberOfCardsOnHandStrategy
public interface WinStrategy {
	public boolean hasWon(Map<Target, PlayerState> playerMap);
}
