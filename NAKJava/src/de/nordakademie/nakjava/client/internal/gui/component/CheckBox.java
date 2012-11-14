package de.nordakademie.nakjava.client.internal.gui.component;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.nordakademie.nakjava.client.internal.gui.ActionContextDelegator;
import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class CheckBox extends JCheckBox implements ActionContextHolder {

	private ActionContextSelector selector;
	private ActionContext context;
	private long currentBatch;

	public CheckBox(ActionContextSelector selector) {
		super();
		currentBatch = Long.MIN_VALUE;
		this.selector = selector;
		this.setEnabled(false);

		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {

				context.perform();

			}
		});
		ActionContextDelegator.getInstance().registerActionContextHolder(this);
	}

	@Override
	public void setActionContext(ActionContext actionContext, long batch) {
		if (currentBatch < batch) {
			currentBatch = batch;
		}

		if (currentBatch > batch) {
			return;
		}

		this.context = actionContext;
		this.setEnabled(true);

	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		return selector.select(context);
	}

	@Override
	public void noActionContextAvailable() {
		this.setEnabled(false);
	}

	@Override
	public void setActionContextSelector(ActionContextSelector selector) {
		this.selector = selector;
	}

	@Override
	public void revokeActionContext(long batch) {
		if (batch >= currentBatch) {
			this.setEnabled(false);
		}

	}

	@Override
	public boolean isDisposed() {
		return this.isShowing();
	}

}
