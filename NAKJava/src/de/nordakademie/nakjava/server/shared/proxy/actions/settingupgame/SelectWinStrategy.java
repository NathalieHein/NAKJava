package de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.SelectAction;

public class SelectWinStrategy extends SelectAction {

	public SelectWinStrategy(String value, long batch, long sessionNr) {
		super(value, batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				session.getModel().setStrategy(
						WinStrategies.getInstance().getStrategyForName(
								getValue()));
				if (!session.isActionInvokerCurrentPlayer()) {
					session.getModel().changeSelfAndOpponent();
				}
				StateMachine.getInstance().run(session.getModel());
			}
		};
	}

}
