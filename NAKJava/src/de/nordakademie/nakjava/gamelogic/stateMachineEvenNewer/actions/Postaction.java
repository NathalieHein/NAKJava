package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;

public class Postaction {

	public static void perform(Map<Target, PlayerState> playerStateMap) {
		// TODO Auto-generated method stub
		StateMachine.run(playerStateMap);
	}

}
