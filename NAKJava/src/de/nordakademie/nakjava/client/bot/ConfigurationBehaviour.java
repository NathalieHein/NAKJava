package de.nordakademie.nakjava.client.bot;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.FinishConfiguringAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class ConfigurationBehaviour implements BotBehaviour {

	@Override
	public State[] getStates() {
		return new State[] { State.CONFIGUREGAME };
	}

	@Override
	public void act(PlayerState state) {
		AbstractActionSelector.selectAction(state.getActions(),
				new ActionContextSelector() {

					@Override
					public boolean select(ActionContext context) {
						return (context instanceof FinishConfiguringAction);
					}
				}).perform();

	}

}
