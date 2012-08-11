package de.nordakademie.nakjava.client.internal.gui;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ActionContextDelegator {

	private List<ActionContextHolder> holders;

	public ActionContextDelegator() {
		holders = new LinkedList<>();
	}

	public void registerActionContextHolder(
			ActionContextHolder actionContextHolder) {
		holders.add(actionContextHolder);
	}

	public void delegateActionContexts(List<ActionContext> actionContexts) {
		List<ActionContextHolder> tempHolders = new LinkedList<>(holders);
		// TODO erst revoke, dann verteilen. noActionAvailable auf die, die
		// nichts bekommen haben. (Kontext: Prüfung auf Gleichheit bei Actions
		// -> isPossiblyStable-Flag)
	}

	public void revokeActionContexts(long batch) {
		for (ActionContextHolder holder : holders) {
			holder.revokeActionContext(batch);
		}
	}
}
