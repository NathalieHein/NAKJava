package de.nordakademie.nakjava.server.internal.actionRules;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.ActionRule;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;

public abstract class StateRule implements ActionRule {

	@Override
	public boolean isRuleApplicable(long sessionId, Player player) {
		Session session = Sessions.getInstance().getSession(sessionId);
		return session.getPlayerStateForPlayer(player).getState() == getState()
				&& isRuleApplicableImpl(session, player);
	}

	protected boolean isRuleApplicableImpl(Session session, Player player) {
		return true;
	}

	public abstract State getState();

}
