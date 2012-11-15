package de.nordakademie.nakjava.server.internal.model;

import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;
import de.nordakademie.nakjava.server.internal.model.transformator.SingleCardTransformator;
import de.nordakademie.nakjava.server.internal.model.transformator.WinStrategyTransformator;

public class Model {
	@VisibleField(targets = { @TargetInState(states = { State.CONFIGUREGAME,
			State.READYTOSTARTSTATE, State.PLAYCARDSTATE,
			State.ADJUSTCARDHANDSTATE, State.STOP }, target = Target.SELF) }, transformer = WinStrategyTransformator.class)
	protected String strategy;
	@VisibleField(targets = { @TargetInState(states = { State.PLAYCARDSTATE,
			State.ADJUSTCARDHANDSTATE, State.STOP }, target = Target.SELF) }, transformer = SingleCardTransformator.class)
	private String lastPlayedCard;
	protected PlayerState self;
	protected PlayerState opponent;
	protected SimulationModel simulationModel;

	public Model(PlayerState playerState) {
		self = playerState;
		// TODO this really doesn't look good
		strategy = (String) WinStrategies.getInstance().getStrategies()
				.toArray()[0];
	}

	public void addPlayerState(PlayerState playerState) {
		if (self != null) {
			opponent = self;
		}
		self = playerState;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
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

	public void setLastPlayedCard(String lastPlayedCard) {
		this.lastPlayedCard = lastPlayedCard;
	}

}
