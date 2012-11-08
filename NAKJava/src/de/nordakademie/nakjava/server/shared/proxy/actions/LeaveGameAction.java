package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class LeaveGameAction extends ActionContext {

	protected LeaveGameAction(long batch, long sessionNr) {
		super(batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				if (!session.isActionInvokerCurrentPlayer()) {
					session.getModel().changeSelfAndOpponent();
				}
				// TODO unregister actionInvoker from Session

				session.getModel().getOpponent()
						.setState(State.OTHERPLAYERLEFTGAME);

			}
		};
	}

}
