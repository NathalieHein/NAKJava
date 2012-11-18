package de.nordakademie.nakjava.client.bot.genericbot.transformers;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategyInformation;

/**
 * Transforms an {@link WinCheck} into a {@link WinCheckMeasurement}. WinChecks
 * can be obtained from {@link WinStrategyInformation} and are part of a
 * {@link WinStrategy}
 * 
 * @author Kai
 * 
 */
public interface WinCheckTransformer {
	/**
	 * Transforms a {@link WinCheck} into a {@link WinCheckMeasurement}.
	 * 
	 * @param check
	 *            the transformer should be able to handle that type.
	 * @return a transformer for this object
	 */
	public WinCheckMeasurement transform(WinCheck check);
}
