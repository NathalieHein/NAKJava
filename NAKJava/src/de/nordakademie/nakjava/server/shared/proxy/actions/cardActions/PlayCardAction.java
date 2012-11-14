package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.internal.model.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;
import de.nordakademie.nakjava.server.shared.proxy.ServerAction;

public class PlayCardAction extends AbstractCardAction {

	public PlayCardAction(String cardName, long sessionNr) {
		super(cardName, sessionNr);
	}

	@Override
	protected ServerAction getAction(long sessionNr) throws RemoteException {
		return new ActionAbstractImpl(sessionNr) {

			@Override
			protected void performImpl(Session session) {
				AbstractCard card = CardLibrary.get().getCards().get(cardName);
				// no need to check whether actionInvoker == currentPlayer -->
				// action would not be verified otherwise

				if (card != null) {
					Model model = session.getModel();
					if (!session.isActionInvokerCurrentPlayer()) {
						model.changeSelfAndOpponent();
					}
					Map<Target, PlayerState> selfOpponentMap = model
							.getSelfOpponentMap();
					card.payImpl(selfOpponentMap);
					card.performActionImpl(selfOpponentMap);
					if (!((InGameSpecificModel) selfOpponentMap
							.get(Target.SELF).getStateSpecificModel())
							.getCards().discardCardFromHand(cardName)) {
						throw new IllegalStateException(
								"Card to be discarded is not in cardhand");
					}
					// if card states: "Play again" than state must be set to
					// "PREACTION"
					StateMachine.getInstance().run(model);
				} else {
					// TODO what if card not found
					// --> should be nothing because should go back to trigger
					// ActionRuleSet
				}
			}
		};
	}
}
