package de.nordakademie.nakjava.server.shared.proxy.actions.editdeck;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * removes currently edited deck and leaves deckEdit State
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class RemoveDeckAction extends ActionContext {

	public RemoveDeckAction(long sessionNr) {
		super(sessionNr);
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
				PlayerState self = model.getSelf();
				EditDeckSpecificModel specificModel = (EditDeckSpecificModel) self
						.getStateSpecificModel();
				self.removeDeck(specificModel.getDeck());
				StateMachine.getInstance().run(session.getModel());

			}
		};
	}
}
