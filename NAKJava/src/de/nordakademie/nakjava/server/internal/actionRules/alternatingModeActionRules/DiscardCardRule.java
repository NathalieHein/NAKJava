package de.nordakademie.nakjava.server.internal.actionRules.alternatingModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class DiscardCardRule extends AlternatingStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		Session session = Sessions.getInstance().getSession(sessionId);
		for (String cardName : ((InGameSpecificModel) session
				.getPlayerStateForPlayer(player).getStateSpecificModel())
				.getCards().getCardsOnHand()) {
			if (CardLibrary.get().getCardForName(cardName).canDrop()) {
				actions.add(new WithdrawCardAction(cardName, sessionId));
			}
		}
		return actions;
	}

	@Override
	public State getState() {
		return State.ADJUSTCARDHANDSTATE;
	}

	@Override
	public boolean isRuleApplicable(long sessionId, Player player) {
		PlayerState self = getSession(sessionId).getModel().getSelf();
		return super.isRuleApplicable(sessionId, player)
				|| self == getSession(sessionId)
						.getPlayerStateForPlayer(player)
				&& self.getState() == State.DISCARDONECARDSTATE;
	}

}
