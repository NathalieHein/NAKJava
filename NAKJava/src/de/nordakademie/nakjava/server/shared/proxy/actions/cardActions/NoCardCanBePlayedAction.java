package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class NoCardCanBePlayedAction extends ActionContext {

	public NoCardCanBePlayedAction(long sessionNr) {
		super(sessionNr);
	}

	@Override
	public ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				if (!session.isActionInvokerCurrentPlayer()) {
					session.getModel().changeSelfAndOpponent();
				}
				PlayCardAuxiliary.checkEndOfGame(session.getModel());

			}
		};
	}

}
