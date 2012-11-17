package de.nordakademie.nakjava.server.shared.proxy.actions.login;

import java.rmi.RemoteException;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Players;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.LoginSpecificModel;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.server.persistence.DeckPersister;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class SubmitPlayerNameAction extends ActionContext {

	public SubmitPlayerNameAction(long sessionNr) {
		super(sessionNr);
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
				String currentName = model.getCurrentPartOfName();
				Players.getInstance().logInPlayerName(currentName);
				self.setName(currentName);
				List<Deck> savedDecks = DeckPersister.getDecks(self);
				self.setSavedDecks(savedDecks);
				StateMachine.getInstance().run(session.getModel());
			}
		};
	}
}
