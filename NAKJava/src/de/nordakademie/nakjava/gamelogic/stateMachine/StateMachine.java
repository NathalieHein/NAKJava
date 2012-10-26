package de.nordakademie.nakjava.gamelogic.stateMachine;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.states.PreActionState;

public class StateMachine {

	private State currentState;

	public StateMachine(Map<Target, PlayerState> playerMap, WinStrategy strategy) {
		currentState = new PreActionState(playerMap, strategy);
	}

	public void receive(Event event) {
		currentState = currentState.performTransition(event);
	}

	public PlayerState getCurrentPlayer() {
		return currentState.getPlayerMap().get(Target.SELF);
	}
}
