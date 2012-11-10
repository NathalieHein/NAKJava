package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;

public class ConfigureGameAction implements StateAction {

	@Override
	public void perform(Model model) {
		// TODO needed?
		// TODO StateMachine model oder session -> could make transformation
		model.getSelf().setStateSpecificModel(new ConfigureGameSpecificModel());
	}
}
