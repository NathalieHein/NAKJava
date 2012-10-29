package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;

public class Preaction {

	public static void perform(Map<Target, PlayerState> playerStateMap) {
		// TODO comment this thing!!!!
		PlayerState currentPlayerState = playerStateMap.get(Target.SELF);
		List<Artifact> producedArtifacts = new LinkedList<>();
		for (Artifact factory : currentPlayerState
				.getTupelsForArtifactType(Artifact.class)) {
			producedArtifacts.addAll(factory.prePlayAction());
		}
		for (Artifact artifact : producedArtifacts) {
			currentPlayerState.getTupelForClass(artifact.getClass()).merge(
					artifact);

		}
		StateMachine.run(playerStateMap);
	}
}