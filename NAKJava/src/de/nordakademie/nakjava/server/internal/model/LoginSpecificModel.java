package de.nordakademie.nakjava.server.internal.model;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.VisibleField.TargetInState;

public class LoginSpecificModel implements StateSpecificModel {
	@VisibleField(targets = { @TargetInState(target = Target.SELF,
			states = { State.LOGIN }) })
	private String currentPartOfName = "";

	public String getCurrentPartOfName() {
		return currentPartOfName;
	}

	public void appendPartOfName(char character) {
		if (character != '\b') {
			currentPartOfName = currentPartOfName + character;
		} else {
			currentPartOfName = currentPartOfName.substring(0,
					currentPartOfName.length() - 1);
		}
	}
}
