package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * ActionContext that initializes all hints to client on server when the
 * client(Player) first registers
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class InitAction extends ActionContext {
	private Player player;

	public InitAction(Player player) throws RemoteException {
		super(0);
		this.player = player;
	}

	/**
	 * adds client to new or existing session
	 */
	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session model) {
				long sessionId = Sessions.getInstance().addPlayer(player);
				setSessionNr(sessionId);
			}

			@Override
			public boolean isServerInternal() {
				return true;
			}

		};
	};
}
