package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class InitAction extends ActionContext {
	private Player player;

	public InitAction(Player player) throws RemoteException {
		super(0, 0);
		this.player = player;
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session model) {
				long sessionId = Sessions.getInstance().addPlayer(player);
				setSessionNr(sessionId);
			}
		};
	}

}
