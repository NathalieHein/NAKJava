package de.nordakademie.nakjava.client.bot;

import de.nordakademie.nakjava.client.internal.gui.GUIHook;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class BotGui implements GUIHook {
	public BotGui(AbstractBotClient bot) {
		bot.setHook(this);
	}

	@Override
	public void newState(PlayerState state) {
		// TODO Auto-generated method stub

	}
}
