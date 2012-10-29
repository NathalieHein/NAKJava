package de.nordakademie.nakjava.client.internal.gui;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ActionContextDelegator {

	private static ActionContextDelegator instance;
	private List<ActionContextHolder> holders;
	private long currentBatch;

	private List<ActionContext> toBeDistributed;

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
				actionContextHolder.setActionContext(context);
			}
		}
	}

	public void delegateActionContexts(List<ActionContext> actionContexts,
			boolean careTaking) {
		List<ActionContextHolder> notMatched = new LinkedList<>(holders);
		toBeDistributed = new LinkedList<>();

		for (ActionContextHolder holder : holders) {
			holder.revokeActionContext(currentBatch);
			if (careTaking) {
				if (!holder.isDisposed()) {
					holders.remove(holder);
				}
			}

		}

		if (actionContexts.size() != 0) {
			currentBatch = actionContexts.get(0).getBatch();
			for (ActionContext context : actionContexts) {

				boolean matched = false;

				for (ActionContextHolder holder : holders) {
					if (holder.isActionContextApplicable(context)) {
						holder.setActionContext(context);
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
