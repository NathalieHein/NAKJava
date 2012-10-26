package de.nordakademie.nakjava.gamelogic.stateMachine;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

public abstract class State {
	protected Map<Target, PlayerState> playerMap;
	protected WinStrategy strategy;

	protected State(Map<Target, PlayerState> playerMap, WinStrategy strategy) {
		this.playerMap = playerMap;
		this.strategy = strategy;
	}

	public State performTransition(Event event) {
		return this;
	};

	public Map<Target, PlayerState> getPlayerMap() {
		return playerMap;
	}
}
