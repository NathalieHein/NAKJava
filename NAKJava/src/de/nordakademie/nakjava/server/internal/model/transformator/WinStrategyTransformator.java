package de.nordakademie.nakjava.server.internal.model.transformator;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategyInformation;
import de.nordakademie.nakjava.server.internal.model.Transformator;

/**
 * transforms the name of the winStrategy into its corresponding
 * WinStrategyInformation-object
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class WinStrategyTransformator implements
		Transformator<String, WinStrategyInformation> {

	@Override
	public WinStrategyInformation transform(String input) {
		return WinStrategies.getInstance().getStrategyInformationForName(input);
	}

}
