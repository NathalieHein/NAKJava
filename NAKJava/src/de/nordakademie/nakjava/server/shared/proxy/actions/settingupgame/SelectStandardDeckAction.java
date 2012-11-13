package de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class SelectStandardDeckAction extends SelectDeckAction {

	public SelectStandardDeckAction(String value, long batch, long sessionNr) {
		super(value, batch, sessionNr);
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
				((ConfigureGameSpecificModel) self.getStateSpecificModel())
						.setChosenDeck(new Deck("StandardDeck", CardLibrary
								.get().getCards().keySet()));
			}
		};
	}

}
