package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

/**
 * plays the card stated by variable cardName
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class PlayCardAction extends AbstractCardAction {

	public PlayCardAction(String cardName, long sessionNr) {
		super(cardName, sessionNr);
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
				PlayCardAuxiliary.playCard(model, cardName);
				PlayCardAuxiliary.checkEndOfGame(model);
			}
		};
	}
}
