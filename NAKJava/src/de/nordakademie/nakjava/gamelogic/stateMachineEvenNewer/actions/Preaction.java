package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;

public class Preaction implements StateAction {

	@Override
	public void perform(Model model) {
		// TODO comment this thing!!!!
		List<Artifact> producedArtifacts = new LinkedList<>();
		for (Artifact factory : ((InGameSpecificModel) model.getSelf()
				.getStateSpecificModel())
				.getTupelsForArtifactType(Artifact.class)) {
			producedArtifacts.addAll(factory.prePlayAction());
		}
		for (Artifact artifact : producedArtifacts) {
			((InGameSpecificModel) model.getSelf().getStateSpecificModel())
					.getTupelForClass(artifact.getClass()).merge(artifact);

		}
		StateMachine.getInstance().run(model);
	}
}