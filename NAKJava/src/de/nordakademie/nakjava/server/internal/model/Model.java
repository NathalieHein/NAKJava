package de.nordakademie.nakjava.server.internal.model;

import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.WinStrategy;

public class Model {
	protected WinStrategy strategy;
	protected PlayerState self;
	protected PlayerState opponent;
	protected SimulationModel simulationModel;

	public Model(PlayerState playerState) {
		self = playerState;
	}

	public void addPlayerState(PlayerState playerState) {
		if (self != null) {
			opponent = self;
		}
		self = playerState;
	}

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

	public SimulationModel getSimulationModel() {
		return simulationModel;
	}

	public void setSimulationModel(SimulationModel simulationModel) {
		this.simulationModel = simulationModel;
	}

	public void createSimulationModel() {
		simulationModel = new SimulationModel(this);
	}

}
