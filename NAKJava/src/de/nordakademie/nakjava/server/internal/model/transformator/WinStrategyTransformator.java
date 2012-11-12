package de.nordakademie.nakjava.server.internal.model.transformator;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategyInformation;
import de.nordakademie.nakjava.server.internal.model.Transformator;

public class WinStrategyTransformator implements
		Transformator<String, WinStrategyInformation> {

	@Override
	public WinStrategyInformation transform(String input) {
		return WinStrategies.getInstance().getStrategyInformationForName(input);
	}

}
