package de.nordakademie.nakjava.server.internal.actionRules;

import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.ActionRule;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Session;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.internal.model.StateSpecificModel;

/**
 * abstract implementation of ActionRule
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public abstract class StateRule implements ActionRule {
	/**
	 * checks whether player's state is equal to the state that this rule is
	 * applicable for
	 */
	@Override
	public boolean isRuleApplicable(long sessionId, Player player) {
		Session session = Sessions.getInstance().getSession(sessionId);
		return session.getPlayerStateForPlayer(player).getState() == getState()
				&& isRuleApplicableImpl(sessionId, player);
	}

	/**
	 * might be overriden by subclasses for additional applicability checks
	 * 
	 * @param sessionId
	 * @param player
	 * @return
	 */
	protected boolean isRuleApplicableImpl(long sessionId, Player player) {
		return true;
	}

	/**
	 * returns the state that this rule is applicable for
	 * 
	 * @return
	 */
	public abstract State getState();

	protected StateSpecificModel getStateSpecificModel(long sessionId,
			Player player) {
		return getPlayerState(sessionId, player).getStateSpecificModel();
	}

	protected PlayerState getPlayerState(long sessionId, Player player) {
		return getSession(sessionId).getPlayerStateForPlayer(player);
	}

	protected Session getSession(long sessionId) {
		return Sessions.getInstance().getSession(sessionId);
	}

	protected PlayerState getOtherPlayerState(long sessionId, Player player) {
		Player otherPlayer = getSession(sessionId).getOneOtherPlayer(player);
		return getPlayerState(sessionId, otherPlayer);
	}

}
