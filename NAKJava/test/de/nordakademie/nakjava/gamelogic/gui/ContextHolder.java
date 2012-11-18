package de.nordakademie.nakjava.gamelogic.gui;

import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ContextHolder implements ActionContextHolder {

	public ActionContext actionContext;
	public long batch;
	public long revokeBatch;
	public boolean noActionContextAvailable;
	public boolean isDisposed;

	@Override
	public void setActionContext(ActionContext actionContext, long batch) {
		this.actionContext = actionContext;
		this.batch = batch;
	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		return context instanceof PlayCardAction;
	}

	@Override
	public void noActionContextAvailable() {
		noActionContextAvailable = true;
	}

	@Override
	public void setActionContextSelector(ActionContextSelector selector) {

	}

	@Override
	public void revokeActionContext(long batch) {
		revokeBatch = batch;
	}

	@Override
	public boolean isDisposed() {
		return isDisposed;
	}

	@Override
	public boolean consumeAction() {
		return false;
	}

}
