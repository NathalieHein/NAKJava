package de.nordakademie.nakjava.server.internal.actionRules.alternatingModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.SimulateCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class PlayCardRule extends AlternatingStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		Session session = getSession(sessionId);
		long batch = getBatch(sessionId);
		for (String cardName : ((InGameSpecificModel) getPlayerState(sessionId,
				player).getStateSpecificModel()).getCards().getCardsOnHand()) {
			AbstractCard card = CardLibrary.get().getCards().get(cardName);
			if (card != null
					&& card.checkPrerequirementsImpl(session.getModel()
							.getSelfOpponentMap())) {
				actions.add(new PlayCardAction(cardName, batch, sessionId));
				actions.add(new SimulateCardAction(cardName, batch, sessionId));
			}
			actions.add(new WithdrawCardAction(cardName, batch, sessionId));
		}
		return actions;
	}

	@Override
	public State getState() {
		return State.PLAYCARDSTATE;
	}

}
