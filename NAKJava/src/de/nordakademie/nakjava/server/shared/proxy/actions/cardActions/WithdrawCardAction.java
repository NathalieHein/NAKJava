package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

/**
 * discards the card on hand given by variable cardName
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public class WithdrawCardAction extends AbstractCardAction {

	public WithdrawCardAction(String cardName, long sessionNr) {
		super(cardName, sessionNr);
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
				if (!((InGameSpecificModel) self.getStateSpecificModel())
						.getCards().discardCardFromHand(cardName)) {
					throw new IllegalStateException(
							"Card to be discarded is not in cardhand");
				}
				self.setState(State.ADJUSTCARDHANDSTATE);
				StateMachine.getInstance().runCurrentState(model);

			}
		};
	}
}
