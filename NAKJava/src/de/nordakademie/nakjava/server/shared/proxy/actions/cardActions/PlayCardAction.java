package de.nordakademie.nakjava.server.shared.proxy.actions.cardActions;

import java.rmi.RemoteException;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.StateMachine;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.RoundResult;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
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
					model.setLastPlayedCard(getCardName());
					PlayerState self = model.getSelf();
					PlayerState opponent = model.getOpponent();
					InGameSpecificModel selfSpecificModel = (InGameSpecificModel) self
							.getStateSpecificModel();
					InGameSpecificModel opponetSpecificModel = null;
					if (opponent.getState() == State.STOP) {
						opponetSpecificModel = (InGameSpecificModel) opponent
								.getStateSpecificModel();
					} else {
						// TODO Fehlerbehandlung
						System.out
								.println("wrong state of opponent in playCard");
					}
					Map<Target, PlayerState> selfOpponentMap = model
							.getSelfOpponentMap();
					card.payImpl(selfOpponentMap);
					card.performActionImpl(selfOpponentMap);
					WinStrategy winStrategy = WinStrategies.getInstance()
							.getStrategyForName(model.getStrategy());
					if (!selfSpecificModel.getCards().discardCardFromHand(
							cardName)) {
						throw new IllegalStateException(
								"Card to be discarded is not in cardhand");
					}
					Map<Target, RoundResult> roundResult = winStrategy
							.getRoundResult(model.getSelfOpponentMap());
					RoundResult selfRoundResult = roundResult.get(Target.SELF);
					if (selfRoundResult != RoundResult.NEUTRAL) {
						self.setState(State.ENDOFGAMESTATE);
						opponent.setState(State.ENDOFGAMESTATE);

						selfSpecificModel.setRoundResult(selfRoundResult);
						opponetSpecificModel.setRoundResult(roundResult
								.get(Target.OPPONENT));
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
