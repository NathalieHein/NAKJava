package de.nordakademie.nakjava.server.shared.proxy.actions.editdeck;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class RemoveDeckAction extends ActionContext {

	public RemoveDeckAction(long batch, long sessionNr) {
		super(batch, sessionNr);
		// TODO Auto-generated constructor stub
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
				// TODO if deckName was changed, then the deck can not been
				// removed
				session.getActionInvoker().getSavedDecks()
						.remove(specificModel.getCurrentPartOfDeckName());
				StateMachine.getInstance().run(session.getModel());

			}
		};
	}
}
