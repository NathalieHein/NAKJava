package de.nordakademie.nakjava.server.shared.proxy.actions.login;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.LoginSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.KeyAction;

public class TypePlayerNameAction extends KeyAction {

	public TypePlayerNameAction(char key, long batch, long sessionNr) {
		super(key, batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				LoginSpecificModel model = (LoginSpecificModel) session
						.getPlayerStateForPlayer(session.getActionInvoker())
						.getStateSpecificModel();
				model.appendPartOfName(getKey());
			}
		};
	}

}
