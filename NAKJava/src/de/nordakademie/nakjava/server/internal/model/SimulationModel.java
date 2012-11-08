package de.nordakademie.nakjava.server.internal.model;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

public class SimulationModel {
	private PlayerState storedSelfDueToSimulation;
	private PlayerState storedOpponentDueToSimulation;
	private int countRounds;

	public SimulationModel(PlayerState self, PlayerState opponent) {
		this.storedSelfDueToSimulation = new PlayerState(self);
		this.storedOpponentDueToSimulation = new PlayerState(opponent);
		countRounds = 0;
	}

	public PlayerState getStoredSelfDueToSimulation() {
		return storedSelfDueToSimulation;
	}

	public PlayerState getStoredOpponentDueToSimulation() {
		return storedOpponentDueToSimulation;
	}

	public int getCountRounds() {
		return countRounds;
	}

}
