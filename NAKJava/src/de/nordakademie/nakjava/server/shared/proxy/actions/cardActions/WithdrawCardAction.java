package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class WithdrawCardAction extends AbstractCardAction {

	public WithdrawCardAction(String cardName, long batch, long sessionNr) {
		super(cardName, batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Model model) {
				// TODO Auto-generated method stub

			}
		};
	}

}
