package de.nordakademie.nakjava.server.shared.proxy.actions.login;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.LoginSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class SubmitPlayerNameAction extends ActionContext {

	public SubmitPlayerNameAction(long batch, long sessionNr) {
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
				PlayerState self = session.getModel().getSelf();
				LoginSpecificModel model = (LoginSpecificModel) self
						.getStateSpecificModel();
				session.getActionInvoker()
						.setName(model.getCurrentPartOfName());
				self.setStateSpecificModel(null);
				// TODO getPlayersDecks
				StateMachine.getInstance().run(session.getModel());
			}
		};
	}

}
