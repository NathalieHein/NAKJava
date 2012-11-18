package de.nordakademie.nakjava.client.internal.gui;

import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Maps {@link ActionContext}s automatically to registered
 * {@link ActionContextHolder}s. These holders register automatically and will
 * be triggered when new actions arrive.
 * 
 * @author Kai
 * 
 */
public class ActionContextDelegator {

	private static ActionContextDelegator instance;
	private List<ActionContextHolder> holders;

	private List<ActionContext> toBeDistributed;
	private long currentBatch;

	/**
	 * Retrieves the delegator which is a singleton instance for central access
	 * 
	 * @return
	 */
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

	/**
	 * registers an {@link ActionContextHolder} and looks whether actions are
	 * available. Thos actions may be passed to the holder.
	 * 
	 * @param actionContextHolder
	 */
	public void registerActionContextHolder(
			ActionContextHolder actionContextHolder) {
		holders.add(actionContextHolder);

		for (ActionContext context : toBeDistributed) {
			if (actionContextHolder.isActionContextApplicable(context)) {
				actionContextHolder.setActionContext(context, currentBatch);
			}
		}
	}

	/**
	 * delegates a list of action contexts to the registered holders
	 * 
	 * @param actionContexts
	 *            actionscontext to delegate
	 * @param batch
	 *            the current wave of actions to identify connecting actions
	 * @param careTaking
	 *            disposed registered holders will be deleted
	 */
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
						if (holder.consumeAction()) {
							break;
						}
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

	}

	/**
	 * Revoke older actioncontexts from the holders
	 * 
	 * @param batch
	 *            the batchnumber identifies the wave in which actions,
	 *            belonging together were distributed
	 */
	public void revokeActionContexts(long batch) {
		for (ActionContextHolder holder : holders) {
			holder.revokeActionContext(batch);
		}
	}
}
