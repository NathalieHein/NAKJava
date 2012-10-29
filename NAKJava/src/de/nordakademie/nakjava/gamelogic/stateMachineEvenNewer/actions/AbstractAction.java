package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;

public abstract class AbstractAction {

	// TODO implement all as singletons + extend Abstractaction
	public static void perform(Map<Target, PlayerState> map) {
		performImpl(map);
		StateMachine.run(map);
	}

	public static void performImpl(Map<Target, PlayerState> map) {

	}
}
