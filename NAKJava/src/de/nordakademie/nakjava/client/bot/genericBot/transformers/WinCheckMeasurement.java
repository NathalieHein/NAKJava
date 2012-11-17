package de.nordakademie.nakjava.client.bot.genericBot.transformers;

import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;

public interface WinCheckMeasurement {
	public double measure(Map<Target, List<? extends Artifact>> genericModel);

}
