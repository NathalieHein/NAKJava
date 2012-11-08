package de.nordakademie.nakjava.server.shared.proxy.actions.editdeck;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.SelectAction;

public class SelectCardForDeckAction extends SelectAction {

	public SelectCardForDeckAction(String value, long batch, long sessionNr) {
		super(value, batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session model) {
				// TODO Auto-generated method stub

			}
		};
	}

}
