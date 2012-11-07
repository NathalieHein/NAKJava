package de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos;

public class LoginSpecificInformation implements StateSpecificInformation {
	private String currentPartOfName;

	public String getCurrentPartOfName() {
		return currentPartOfName;
	}

	public LoginSpecificInformation(String currentPartOfName) {
		this.currentPartOfName = currentPartOfName;
	}

}
