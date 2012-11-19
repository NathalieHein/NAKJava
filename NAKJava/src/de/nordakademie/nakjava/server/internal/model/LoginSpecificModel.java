package de.nordakademie.nakjava.server.internal.model;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;

/**
 * SpecificModel for one Player that contains specific information for state
 * Login
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class LoginSpecificModel implements StateSpecificModel {
	@VisibleField(targets = { @TargetInState(target = Target.SELF,
			states = { State.LOGIN }) })
	private String currentPartOfName = "";

	public String getCurrentPartOfName() {
		return currentPartOfName;
	}

	/**
	 * appends the character to the current name, if '\b': deletes the last
	 * character
	 * 
	 * @param character
	 *            that is to be appended to current player name
	 */
	public void appendPartOfName(char character) {
		if (character != '\b') {
			currentPartOfName = currentPartOfName + character;
		} else {
			currentPartOfName = currentPartOfName.substring(0,
					currentPartOfName.length() - 1);
		}
	}
}
