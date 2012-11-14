package de.nordakademie.nakjava.client.internal.gui;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ActionContextDelegator {

	private static ActionContextDelegator instance;
	private List<ActionContextHolder> holders;

	private List<ActionContext> toBeDistributed;
	private long currentBatch;

	public static synchronized ActionContextDelegator getInstance() {
		if (instance == null) {
			instance = new ActionContextDelegator();
		}

		return instance;
	}

	private ActionContextDelegator() {
		holders = new LinkedList<>();
		toBeDistributed = new LinkedList<>();
		currentBatch = Long.MIN_VALUE;
	}

	public void registerActionContextHolder(
			ActionContextHolder actionContextHolder) {
		holders.add(actionContextHolder);

		for (ActionContext context : toBeDistributed) {
			if (actionContextHolder.isActionContextApplicable(context)) {
				actionContextHolder.setActionContext(context, currentBatch);
			}
		}
	}

	public void delegateActionContexts(List<ActionContext> actionContexts,
			long batch, boolean careTaking) {
		List<ActionContextHolder> notMatched = new LinkedList<>(holders);
		toBeDistributed = new LinkedList<>();
		currentBatch = batch;

		List<ActionContextHolder> toRemove = new LinkedList<>();

		for (ActionContextHolder holder : holders) {
			holder.revokeActionContext(batch - 1);
			if (careTaking) {
				if (!holder.isDisposed()) {
					toRemove.add(holder);
				}
			}

		}

		holders.removeAll(toRemove);

		if (actionContexts.size() != 0) {
			for (ActionContext context : actionContexts) {

				boolean matched = false;

				for (ActionContextHolder holder : holders) {
					if (holder.isActionContextApplicable(context)) {
						holder.setActionContext(context, batch);
						notMatched.remove(holder);
						matched = true;
						break;
					}
				}
				if (!matched) {
					toBeDistributed.add(context);
				}
			}

			for (ActionContextHolder holder : notMatched) {
				holder.noActionContextAvailable();
			}
		}

		// TODO possibly Stable Flag??
	}

	public void revokeActionContexts(long batch) {
		for (ActionContextHolder holder : holders) {
			holder.revokeActionContext(batch);
		}
	}
}
