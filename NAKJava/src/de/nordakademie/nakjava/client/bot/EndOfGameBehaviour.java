package de.nordakademie.nakjava.client.bot;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.proxy.actions.PlayAgainAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class EndOfGameBehaviour implements BotBehaviour {

	@Override
	public State[] getStates() {
		return new State[] { State.ENDOFGAMESTATE, State.OTHERPLAYERLEFTGAME };
	}

	@Override
	public void act(PlayerState state) {
		AbstractActionSelector.selectAction(state.getActions(),
				new ActionContextSelector() {

					@Override
					public boolean select(ActionContext context) {
						return context instanceof PlayAgainAction;

					}
				}).perform();
	}

}
