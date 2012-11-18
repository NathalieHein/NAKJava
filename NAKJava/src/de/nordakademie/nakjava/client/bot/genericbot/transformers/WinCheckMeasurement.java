package de.nordakademie.nakjava.client.bot.genericbot.transformers;

import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;

/**
 * Transformed {@link WinCheck} to give a analoge value for a digital
 * {@link WinCheck}. For each type of {@link WinCheck} should exist one
 * {@link WinCheckMeasurement} type. For each {@link WinCheck} exists in the end
 * one {@link WinCheckMeasurement}. The bot has to deal with the values itself.
 * 
 * 
 */
public interface WinCheckMeasurement {
	/**
	 * Measures a distribution of ressources in the view of the distance to win.
	 * 
	 * @param genericModel
	 *            artifact distribution
	 * @return double 0..1 progress of winning
	 */
	public double measure(Map<Target, List<? extends Artifact>> genericModel);

}
