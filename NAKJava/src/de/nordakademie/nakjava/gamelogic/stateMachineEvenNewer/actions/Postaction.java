package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.model.Model;

public class Postaction implements StateAction {

	@Override
	public void perform(Model model) {
		// TODO Auto-generated method stub
		StateMachine.getInstance().run(model);
	}
}
