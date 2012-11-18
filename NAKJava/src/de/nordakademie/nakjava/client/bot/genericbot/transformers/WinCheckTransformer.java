package de.nordakademie.nakjava.client.bot.genericbot.transformers;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;

public interface WinCheckTransformer {
	public WinCheckMeasurement transform(WinCheck check);
}
