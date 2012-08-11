package de.nordakademie.nakjava.client.internal.gui;

import java.rmi.RemoteException;
import java.util.List;

import de.nordakademie.nakjava.client.internal.Client;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public abstract class AbstractGUIClient extends Client {

	protected ActionContextDelegator delegator;

	protected AbstractGUIClient() throws RemoteException {
		super();
		delegator = new ActionContextDelegator();
	}

	@Override
	public void stateChanged(PlayerState state) throws RemoteException {
		playerActionsChanged(state.getActions());
		playerModelChanged(state.getModel());
	}

	public void playerActionsChanged(List<ActionContext> actions) {

	}

	public abstract void playerModelChanged(PlayerModel model);

}
