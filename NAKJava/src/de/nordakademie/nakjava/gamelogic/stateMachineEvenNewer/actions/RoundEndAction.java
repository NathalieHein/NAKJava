package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.model.Model;

/**
 * perform change of turn of Self and Opponent-Player at end of round
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class RoundEndAction implements StateAction {

	@Override
	public void perform(Model model) {
		model.changeSelfAndOpponent();
		StateMachine.getInstance().run(model);
	}

}
