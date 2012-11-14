package de.nordakademie.nakjava.server.shared.proxy.actions.editdeck;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.server.persistence.DeckPersister;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class SaveDeckAction extends ActionContext {

	public SaveDeckAction(long sessionNr) {
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
				EditDeckSpecificModel specificModel = (EditDeckSpecificModel) model
						.getSelf().getStateSpecificModel();
				Set<String> cards = new HashSet<>();
				for (Entry<String, Boolean> entry : specificModel
						.getChosenCards().entrySet()) {
					if (entry.getValue()) {
						cards.add(entry.getKey());
					}
				}
				Player player = session.getActionInvoker();
				Deck deck = player.addDeck(
						specificModel.getCurrentPartOfDeckName(), cards);
				DeckPersister.saveDeck(deck, player);
				StateMachine.getInstance().run(session.getModel());
			}
		};
	}
}
