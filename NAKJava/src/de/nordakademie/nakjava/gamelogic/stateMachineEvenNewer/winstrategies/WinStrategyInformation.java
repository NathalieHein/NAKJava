package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.io.Serializable;

/**
 * Serializable information about a {@link WinStrategy} which describes a type
 * of winning method.
 * 
 * @author Kai
 * 
 */
public class WinStrategyInformation implements Serializable {
	private String name;
	private String description;

	private WinCheck[] winChecks;

	/**
	 * An information is automaticall instanciated by {@link WinStrategies}. It
	 * contains also WinChecks which might be transformed by the client.
	 * 
	 * @param name
	 * @param description
	 * @param checks
	 */
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
