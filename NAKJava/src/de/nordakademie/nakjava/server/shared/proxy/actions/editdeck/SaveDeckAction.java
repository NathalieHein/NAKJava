package de.nordakademie.nakjava.server.shared.proxy.actions.editdeck;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.actionRules.uniqueModeActionRules.SaveDeckRule;
import de.nordakademie.nakjava.server.internal.model.EditDeckSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.persistence.Deck;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * SaveDeckAction works as follows:
 * 
 * - previous name of edited deck is not current name --> new {@link Deck} will
 * be created and saved
 * 
 * - previous name of edited deck is same as current name --> deck will be
 * overwritten
 * 
 * - current name already in use for other than currently edited deck --> no
 * {@link SaveDeckAction} will be allowed (s. {@link SaveDeckRule}
 * 
 * @author Nathalie Hein (12154)
 * 
 */
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
				PlayerState self = model.getSelf();
				EditDeckSpecificModel specificModel = (EditDeckSpecificModel) self
						.getStateSpecificModel();
				Set<String> cards = new HashSet<>();
				for (Entry<String, Boolean> entry : specificModel
						.getChosenCards().entrySet()) {
					if (entry.getValue()) {
						cards.add(entry.getKey());
					}
				}
				Deck deck = self.addDeck(
						specificModel.getCurrentPartOfDeckName(), cards);
				StateMachine.getInstance().run(session.getModel());
			}
		};
	}
}
