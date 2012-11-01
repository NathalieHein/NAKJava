package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class WithdrawCardAction extends AbstractCardAction {

	public WithdrawCardAction(String cardName, long batch, long sessionNr) {
		super(cardName, batch, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				PlayerState self = session.getModel().getSelf();
				if (!self.getCards().discardCardFromHand(cardName)) {
					throw new IllegalStateException(
							"Card to be discarded is not in cardhand");
				}
				if (self.getState() == State.ADJUSTCARDHANDSTATE) {
					self.setState(State.POSTACTIONSTATE);
				}
				StateMachine.getInstance().run(session.getModel());

			}
		};
	}
}
