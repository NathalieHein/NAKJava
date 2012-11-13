package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

		this.setEnabled(true);

		this.actionContext = actionContext;
		this.removeActionListener(this);
		this.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		final Lock lock = new ReentrantLock();
		lock.lock();

		new Thread(new Runnable() {

			@Override
			public void run() {
				lock.lock();
				try {
					if (actionContext != null) {
						try {
							Button.this.setEnabled(false);
							actionContext.perform();
							actionContext = null;
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} finally {
					lock.unlock();
				}

			}
		}).start();

		lock.unlock();

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
		removeActionListener(this);
	}

}
