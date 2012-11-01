package de.nordakademie.nakjava.server.shared.proxy.actions.editdeck;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class SaveDeckAction extends ActionContext {

	public SaveDeckAction(long batch, long sessionNr) {
		super(batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				// TODO save deck accordingly
				if (!session.isActionInvokerCurrentPlayer()) {
					session.getModel().changeSelfAndOpponent();
				}
				// TODO to return to choosedeck (right now) or shall we assume
				// that saved
				// deck was selected as well
				StateMachine.getInstance().run(session.getModel());
			}
		};
	}

}
