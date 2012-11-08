package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.model.Model;

public class Preaction implements StateAction {

	@Override
	public void perform(Model model) {
		// TODO comment this thing!!!!
		List<Artifact> producedArtifacts = new LinkedList<>();
		for (Artifact factory : model.getSelf().getTupelsForArtifactType(
				Artifact.class)) {
			producedArtifacts.addAll(factory.prePlayAction());
		}
		for (Artifact artifact : producedArtifacts) {
			model.getSelf().getTupelForClass(artifact.getClass())
					.merge(artifact);

		}
		StateMachine.getInstance().run(model);
	}
}