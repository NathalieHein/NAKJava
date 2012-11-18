package de.nordakademie.nakjava.server.internal.actionRules.alternatingModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.NoCardCanBePlayedAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.SimulateCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class PlayCardRule extends AlternatingStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		Session session = getSession(sessionId);
		int[] arr = { 1, 5, 10 };
		List<String> cardsOnHand = ((InGameSpecificModel) getPlayerState(
				sessionId, player).getStateSpecificModel()).getCards()
				.getCardsOnHand();
		for (String cardName : cardsOnHand) {
			AbstractCard card = CardLibrary.get().getCards().get(cardName);
			if (card != null
					&& card.checkPrerequirementsImpl(session.getModel()
							.getSelfOpponentMap())) {
				actions.add(new PlayCardAction(cardName, sessionId));
			}
			if (CardLibrary.get().getCardForName(cardName).canDrop()) {
				actions.add(new WithdrawCardAction(cardName, sessionId));
			}
		}
		if (cardsOnHand.size() == 0) {
			final ActionContext noCardAction = new NoCardCanBePlayedAction(
					sessionId);
			actions.add(noCardAction);
			new Thread(new Runnable() {

				@Override
				public void run() {
					noCardAction.perform();
				}
			}).start();

		} else {
			actions.add(new SimulateCardAction(arr, sessionId));
		}
		return actions;
	}

	@Override
	public State getState() {
		return State.PLAYCARDSTATE;
	}

}
