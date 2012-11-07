package de.nordakademie.nakjava.server.shared.serial.stateSpecificInfos;

import java.util.Map;

public class ConfigurationSpecificInformation implements
		StateSpecificInformation {
	private Map<String, String> winStrategyDescription;

	public ConfigurationSpecificInformation(
			Map<String, String> winStrategyDescription) {
		this.winStrategyDescription = winStrategyDescription;
	}

	public Map<String, String> getWinStrategyDescription() {
		return winStrategyDescription;
	}

}
