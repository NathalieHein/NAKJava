package de.nordakademie.nakjava.client.internal.gui;

import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public interface ActionContextHolder {
	public void setActionContext(ActionContext actionContext);

	public boolean isActionContextApplicable(ActionContext context);

	public void noActionContextAvailable();

	public void setActionContextSelector(ActionContextSelector selector);

	public void revokeActionContext(long batch);
}
