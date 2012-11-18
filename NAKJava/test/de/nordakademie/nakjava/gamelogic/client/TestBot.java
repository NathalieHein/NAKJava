package de.nordakademie.nakjava.gamelogic.client;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.bot.AbstractBotClient;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class TestBot extends AbstractBotClient {

	public boolean init;

	public TestBot() throws RemoteException {
		super(null, false);
	}

	@Override
	public void turn(PlayerState state) {

	}

	@Override
	public void drop(PlayerState state) {

	}

	@Override
	public void initBot(PlayerState state) {
		init = true;

	}

	@Override
	public void gameFinished(RoundResult result) {

	}
}
