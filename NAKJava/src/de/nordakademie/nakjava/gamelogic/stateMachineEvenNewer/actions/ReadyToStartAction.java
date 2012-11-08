package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.Model;

public class ReadyToStartAction implements StateAction {

	@Override
	public void perform(Model model) {
		if (model.getOpponent().getState() == State.READYTOSTARTSTATE) {
			model.getOpponent().setState(State.STOP);
			StateMachine.getInstance().run(model);
		}
	}

}
