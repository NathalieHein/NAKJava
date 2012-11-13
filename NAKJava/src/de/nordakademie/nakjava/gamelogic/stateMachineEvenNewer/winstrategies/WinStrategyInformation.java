package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.io.Serializable;

public class WinStrategyInformation implements Serializable {
	private String name;
	private String description;

	private WinCheck[] winChecks;

	public WinStrategyInformation(String name, String description,
			WinCheck[] checks) {
		this.name = name;
		this.description = description;
		this.winChecks = checks;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public WinCheck[] getWinChecks() {
		return winChecks;
	}

	@Override
	public String toString() {
		return name;
	}

}
