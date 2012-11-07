package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos.StateSpecificInformation;

public class PlayerModel implements Serializable {
	private Map<Target, State> targetToState;
	private Map<Target, String> targetToName;
	private StateSpecificInformation stateSpecificInfos;

	public Map<Target, State> getTargetToState() {
		return targetToState;
	}

	public void setTargetToState(Map<Target, State> targetToState) {
		this.targetToState = targetToState;
	}

	public Map<Target, String> getTargetToName() {
		return targetToName;
	}

	public void setTargetToName(Map<Target, String> targetToName) {
		this.targetToName = targetToName;
	}

	public StateSpecificInformation getStateSpecificInfos() {
		return stateSpecificInfos;
	}

	public void setStateSpecificInfos(StateSpecificInformation stateSpecificInfos) {
		this.stateSpecificInfos = stateSpecificInfos;
	}

}
