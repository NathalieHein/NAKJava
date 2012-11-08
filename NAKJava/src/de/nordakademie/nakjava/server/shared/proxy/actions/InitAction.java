package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;

public class InitAction extends ActionAbstractImpl {
	private Player player;

	public InitAction(Player player) throws RemoteException {
		super(0);
		this.player = player;
	}

	@Override
	protected void performImpl(Session model) {
		long sessionId = Sessions.getInstance().addPlayer(player);
		setSessionNr(sessionId);
	};
}
