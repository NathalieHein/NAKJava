package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions;

import de.nordakademie.nakjava.server.internal.model.Model;

public class GameEndAction implements StateAction {

	@Override
	public void perform(Model model) {
		model.setLastPlayedCard(null);
	}

}
