package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Model;

public class RoundEndAction implements StateAction {

	@Override
	public void perform(Model model) {
		model.changeSelfAndOpponent();
		StateMachine.getInstance().run(model);
	}

}
