package de.nordakademie.nakjava.server.shared.proxy.actions.editdeck;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.SelectAction;

/**
 * selects/deselects single card for the edited deck depending on current value
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class SelectCardForDeckAction extends SelectAction {

	public SelectCardForDeckAction(String value, long sessionNr) {
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
				EditDeckSpecificModel specificModel = (EditDeckSpecificModel) model
						.getSelf().getStateSpecificModel();
				specificModel.reverseSelectionOfCard(getValue());
			}
		};
	}
}
