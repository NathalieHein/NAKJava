package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

public class WinStrategyInformation {
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

}
