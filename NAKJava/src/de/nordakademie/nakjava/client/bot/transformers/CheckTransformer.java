package de.nordakademie.nakjava.client.bot.transformers;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;

public @interface CheckTransformer {
	Class<? extends WinCheck> transformTarget();
}
