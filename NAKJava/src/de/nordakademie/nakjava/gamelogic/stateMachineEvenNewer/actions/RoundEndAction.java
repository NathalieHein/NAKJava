package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;

public class RoundEndAction {

	public static void perform(Map<Target, PlayerState> playerStateMap) {
		PlayerState current = playerStateMap.get(Target.SELF);
		playerStateMap.remove(Target.SELF);
		playerStateMap.put(Target.SELF, playerStateMap.get(Target.OPPONENT));
		playerStateMap.remove(Target.OPPONENT);
		playerStateMap.put(Target.OPPONENT, current);

		StateMachine.run(playerStateMap);
	}

}
