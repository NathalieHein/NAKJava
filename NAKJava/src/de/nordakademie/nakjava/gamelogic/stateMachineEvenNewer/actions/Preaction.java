package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

public class Preaction {
	// TODO how does it work with generics
	public static void perform(Map<Target, PlayerState> playerStateMap) {
		/*
		 * PlayerState currentPlayerState = playerStateMap.get(Target.SELF); for
		 * (Factory factory : currentPlayerState
		 * .getTupelsForArtifactType(Factory.class)) { Collection<>
		 * producedArtifacts = factory.prePlayAction(); for (<T> artifact :
		 * producedArtifacts) {
		 * currentPlayerState.getTupelForClass(artifact.class); } }
		 */

		de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine
				.run(playerStateMap);
	}
}