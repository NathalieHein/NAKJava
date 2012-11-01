package de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.SelectAction;

public class SelectDeckAction extends SelectAction {

	public SelectDeckAction(String value, long batch, long sessionNr) {
		super(value, batch, sessionNr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				if (!session.isActionInvokerCurrentPlayer()) {
					session.getModel().changeSelfAndOpponent();
				}
				StateMachine.getInstance().run(session.getModel());
			}
		};

	}
}
