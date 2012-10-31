package de.nordakademie.nakjava.client.bot.transformers;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;

public interface WinCheckMeasurement {
	public double measure(Map<Target, PlayerModel> model);

}
