package de.nordakademie.nakjava.client.bot;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public interface BotBehaviour {
	public State[] getStates();

	public void act(PlayerState state);
}
