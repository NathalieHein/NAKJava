package de.nordakademie.nakjava.server.internal.actionRules.alternatingModeActionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class AdjustCardHandRule extends AlternatingStateRule {

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		Session session = Sessions.getInstance().getSession(sessionId);
		long batch = session.getBatch().getCurrentBatchNr();
		for (String cardName : session.getPlayerStateForPlayer(player)
				.getCards().getCardsOnHand()) {
			actions.add(new WithdrawCardAction(cardName, batch, sessionId));
		}
		return actions;
	}

	@Override
	public State getState() {
		return State.ADJUSTCARDHANDSTATE;
	}

}