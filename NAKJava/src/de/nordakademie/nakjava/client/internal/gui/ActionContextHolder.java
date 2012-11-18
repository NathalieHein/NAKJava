package de.nordakademie.nakjava.client.internal.gui;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * An actionContext Holder is a gui component which can hold an action for
 * triggering it when its triggered itself. Some holers may contain one action
 * some even more. Those holders have to register at the action context
 * delegator.
 * 
 * @author Kai
 * 
 */
public interface ActionContextHolder {
	/***
	 * Sets an actionContext at the holder, after it applied the context. For
	 * registering a wave of actions a batch number is submitted. For each wave
	 * of actions the batch number will increase
	 */
	public void setActionContext(ActionContext actionContext, long batch);

	/**
	 * Asks the holder whether it wants an action context.
	 * 
	 * @param context
	 * @return
	 */
	public boolean isActionContextApplicable(ActionContext context);

	/**
	 * If the delegator has no actionContext in this round this method is
	 * invoked. on all holders which did not receive an action.
	 */
	public void noActionContextAvailable();

	/**
	 * Set a selector for action contexts on the holder.
	 * 
	 * @param selector
	 */
	public void setActionContextSelector(ActionContextSelector selector);

	/**
	 * Revoke actioncontexts up to this batch number. This is called to
	 * invalidate the gui after one actioncontext is perfomed.
	 * 
	 * @param batch
	 */
	public void revokeActionContext(long batch);

	/**
	 * returns whether this component is disposed. (should be standard for gui
	 * componnets)
	 * 
	 * @return
	 */
	public boolean isDisposed();

	/**
	 * Returns whether the holder should be the only receiver this kind of
	 * actions.
	 * 
	 * @return
	 */
	public boolean consumeAction();
}
