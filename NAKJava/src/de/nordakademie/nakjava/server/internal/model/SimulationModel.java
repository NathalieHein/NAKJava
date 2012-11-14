package de.nordakademie.nakjava.server.internal.model;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

public class SimulationModel extends Model {
	private int countRounds;

	public SimulationModel(PlayerState self, PlayerState opponent,
			String winStrategy) {
		super(self);
		this.opponent = opponent;
		this.strategy = winStrategy;
		countRounds = 0;
	}

	public int getCountRounds() {
		return countRounds;
	}

	public SimulationModel(Model model) {
		// TODO deepCopy via write/read of serializable Objects
		this(model.getSelf(), model.getOpponent(), model.getStrategy());
	}

}
