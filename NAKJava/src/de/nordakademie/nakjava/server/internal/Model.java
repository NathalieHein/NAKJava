package de.nordakademie.nakjava.server.internal;

import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.WinStrategy;

public class Model {
	private WinStrategy strategy;
	private PlayerState self;
	private PlayerState opponent;

	public WinStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(WinStrategy strategy) {
		this.strategy = strategy;
	}

	public PlayerState getSelf() {
		return self;
	}

	public PlayerState getOpponent() {
		return opponent;
	}

	public void changeSelfAndOpponent() {
		PlayerState currentSelf = self;
		self = opponent;
		opponent = currentSelf;
	}

	public Map<Target, PlayerState> getSelfOpponentMap() {
		Map<Target, PlayerState> map = new HashMap<>();
		map.put(Target.SELF, self);
		map.put(Target.OPPONENT, opponent);
		return map;
	}
}
