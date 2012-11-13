package de.nordakademie.nakjava.server.internal.actionRules;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.nakjava.server.internal.ActionRule;
import de.nordakademie.nakjava.server.internal.Player;
import de.nordakademie.nakjava.server.internal.Sessions;
import de.nordakademie.nakjava.server.shared.proxy.actions.LeaveGameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class LeaveGameRule implements ActionRule {

	@Override
	public boolean isRuleApplicable(long sessionId, Player player) {
		return true;
	}

	@Override
	public List<ActionContext> applyRule(long sessionId, Player player) {
		List<ActionContext> actions = new ArrayList<>();
		long batch = Sessions.getInstance().getSession(sessionId).getBatch()
				.getCurrentBatchNr();
		actions.add(new LeaveGameAction(sessionId));
		return actions;
	}

}
