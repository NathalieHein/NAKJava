package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import de.nordakademie.nakjava.client.internal.gui.ActionContextDelegator;
import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class Button extends JButton implements ActionListener,
		ActionContextHolder {

	private ActionContext actionContext;
	private ActionContextSelector selector;
	private long currentBatch;

	private boolean initialized = false;

	public Button(String text, ActionContextSelector selector,
			boolean autoRegister) {
		super(text);
		currentBatch = Long.MIN_VALUE;
		this.selector = selector;

		if (actionContext == null) {
			setEnabled(false);
		}
		if (autoRegister) {
			ActionContextDelegator.getInstance().registerActionContextHolder(
					this);
		}
	}

	public Button(String text, final Class<? extends ActionContext> actionClass) {
		this(text, new ActionContextSelector() {

			@Override
			public boolean select(ActionContext context) {
				return actionClass.isAssignableFrom(context.getClass());
			}
		}, true);
	}

	@Override
	public void setActionContext(ActionContext actionContext) {

		if (!this.isEnabled()) {
			this.setEnabled(true);
		}

		this.actionContext = actionContext;
		if (!initialized) {
			this.addActionListener(this);
			initialized = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (actionContext != null) {
			try {
				actionContext.perform();
				actionContext = null;
				this.setEnabled(false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		if (selector == null) {
			return false;
		} else {
			return selector.select(context);
		}

	}

	@Override
	public void noActionContextAvailable() {
		removeActionContext();

	}

	@Override
	public void setActionContextSelector(ActionContextSelector selector) {
		this.selector = selector;
	}

	@Override
	public void revokeActionContext(long batch) {
		if (batch >= currentBatch) {
			removeActionContext();
		}
	}

	@Override
	public boolean isDisposed() {
		return this.isShowing();
	}

	private void removeActionContext() {
		this.setEnabled(false);
		actionContext = null;
	}

}
