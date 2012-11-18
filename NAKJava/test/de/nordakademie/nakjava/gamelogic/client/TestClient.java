package de.nordakademie.nakjava.gamelogic.client;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.internal.AbstractClient;
import de.nordakademie.nakjava.client.internal.gui.GUIHook;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class TestClient extends AbstractClient {

	public PlayerState state;

	protected TestClient(GUIHook gui) throws RemoteException {
		super(gui);
	}

	@Override
	protected void stateChange(PlayerState state) {
		super.stateChange(state);
		this.state = state;
	}

}
