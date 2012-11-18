package de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.SelectAction;

/**
 * Sets the WinStrategy in model to the value that the client selected
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class SelectWinStrategy extends SelectAction {

	public SelectWinStrategy(String value, long sessionNr) {
		super(value, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				Model model = session.getModel();
				if (!session.isActionInvokerCurrentPlayer()) {
					model.changeSelfAndOpponent();
				}
				model.setStrategy(getValue());
			}
		};
	}

}
