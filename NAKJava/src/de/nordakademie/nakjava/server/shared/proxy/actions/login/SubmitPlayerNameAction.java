package de.nordakademie.nakjava.server.shared.proxy.actions.login;

import java.rmi.RemoteException;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Player;
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
				Player player = session.getActionInvoker();
				LoginSpecificModel model = (LoginSpecificModel) self
						.getStateSpecificModel();
				player.setName(model.getCurrentPartOfName());
				List<Deck> savedDecks = DeckPersister.getDecks(player);
				player.setSavedDecks(savedDecks);
				// TODO set savedDeckNames to next SpecificModel -> StateMachine
				// kennt Session oder StateSpecificModel hier setzen-> beides
				// scheiße!
				StateMachine.getInstance().run(session.getModel());
			}
		};
	}
}
