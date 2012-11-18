package de.nordakademie.nakjava.client.bot;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

/**
 * A behaviour describes a reaction to a certain {@link State} passed to the
 * bot. Behaviours are collected at runtime and a dynamic lookup is performed.
 * 
 * @author Kai
 * 
 */
public interface BotBehaviour {
	/**
	 * Return the states this behaviour is interested in.
	 * 
	 * @return
	 */
	public State[] getStates();

	/**
	 * What shall be the action to the given states.
	 * 
	 * @param state
	 */
	public void act(PlayerState state);
}
