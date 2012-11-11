package de.nordakademie.nakjava.server.shared.serial;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos.StateSpecificInformation;

public class PlayerModel implements Serializable {
	private State state;
	private String name;
	private StateSpecificInformation stateSpecificInfos;
	private Map<String, Object> genericTransfer;

	public PlayerModel() {
		genericTransfer = new HashMap<>();
	}

	public Map<String, Object> getGenericTransfer() {
		return genericTransfer;
	}

	public void putGenericTransferObject(String id, Object obj) {
		genericTransfer.put(id, obj);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StateSpecificInformation getStateSpecificInfos() {
		return stateSpecificInfos;
	}

	public void setStateSpecificInfos(
			StateSpecificInformation stateSpecificInfos) {
		this.stateSpecificInfos = stateSpecificInfos;
	}

}
