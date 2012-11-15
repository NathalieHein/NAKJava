package de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.ConfigureGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.SelectAction;

public class SelectDeckAction extends SelectAction {

	public SelectDeckAction(String value, long sessionNr) {
		super(value, sessionNr);
		// TODO Auto-generated constructor stub
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
						.setChosenDeck(self.getDeckWithName(getValue()));

			}
		};

	}
}
