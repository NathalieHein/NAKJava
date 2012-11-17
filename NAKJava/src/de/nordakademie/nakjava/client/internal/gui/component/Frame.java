package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.nordakademie.nakjava.client.internal.gui.ActionContextDelegator;
import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.proxy.actions.LeaveGameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class Frame extends JFrame implements ActionContextHolder {
	private ActionContext actionContext = null;
	private long currentBatch;
	private Lock lock;
	private Condition condition;

	public Frame() {
		currentBatch = Long.MIN_VALUE;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		lock = new ReentrantLock();
		condition = lock.newCondition();
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (actionContext != null) {
					actionContext.perform();
				} else {
					int result = JOptionPane
							.showConfirmDialog(
									Frame.this,
									"Keine Möglichkeit zum sanften Beenden, soll auf die nächste gewartet werden?",
									"", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								lock.lock();
								try {
									if (!condition.await(2, TimeUnit.SECONDS)) {
										System.exit(0);
									} else {
										actionContext.perform();
									}
								} catch (InterruptedException e) {
								}

								lock.unlock();
							}
						}).start();
					} else {
						System.exit(0);
					}

				}
			}

		});
		ActionContextDelegator.getInstance().registerActionContextHolder(this);
	}

	@Override
	public void setActionContext(ActionContext actionContext, long batch) {
		if (batch > currentBatch) {
			lock.lock();
			condition.signal();
			currentBatch = batch;
			this.actionContext = actionContext;
			lock.unlock();
		}

	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		return (context instanceof LeaveGameAction);
	}

	@Override
	public void noActionContextAvailable() {
		actionContext = null;

	}

	@Override
	public void setActionContextSelector(ActionContextSelector selector) {
	}

	@Override
	public void revokeActionContext(long batch) {
		if (batch > currentBatch) {
			actionContext = null;
		}

	}

	@Override
	public boolean isDisposed() {
		return true;
	}

	@Override
	public boolean consumeAction() {
		return false;
	}
}
