package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Model;

public class ReadyToStartAction implements StateAction {

	@Override
	public void perform(Model model) {
		if (model.getOpponent().getState() == State.READYTOSTARTSTATE) {
			model.getSelf().setState(State.GAMETOSTARTSTATE);
			StateMachine.getInstance().run(model);
		}
	}

}
