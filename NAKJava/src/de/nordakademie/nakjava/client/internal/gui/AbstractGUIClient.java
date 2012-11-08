package de.nordakademie.nakjava.client.internal.gui;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.nordakademie.nakjava.client.internal.Client;
import de.nordakademie.nakjava.client.shared.ClientAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public abstract class AbstractGUIClient extends Client {

	protected ActionContextDelegator delegator;

	protected AbstractGUIClient() throws RemoteException {
		super();
	}

	@Override
	public void stateChanged(PlayerState state) throws RemoteException {
		playerActionsChanged(state.getActions());
		playerModelChanged(state.getModel());
	}

	private void playerActionsChanged(List<ActionContext> actions) {
		extendActionContext(actions);
		delegator.delegateActionContexts(actions, true);
	}

	@Override
	protected void preCheckin() {
		JFrame frame = new JFrame();
		delegator = ActionContextDelegator.getInstance();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		preCheckinFinalValues.add(frame);
	}

	protected JFrame getFrame() {
		return ((JFrame) preCheckinFinalValues.get(0));
	}

	/**
	 * Extends the {@link ActionContext} with {@link ClientAction}s. The
	 * following functionality is added: <br/>
	 * <ul>
	 * <li>After an Action is performed a client side invalidation is performed
	 * to block gui elements until new actions are available. Hence the user
	 * does not see an inconsistent state.</li>
	 * </ul>
	 * 
	 * @param actions
	 */
	private void extendActionContext(List<ActionContext> actions) {
		for (final ActionContext actionContext : actions) {
			actionContext.addPreClientAction(new ClientAction() {
				@Override
				public void perform() {
					AbstractGUIClient.this.revokeActionContexts(actionContext
							.getBatch());
				}
			});
		}
	}

	public abstract void playerModelChanged(PlayerModel model);

	public void revokeActionContexts(long batch) {
		delegator.revokeActionContexts(batch);
	}

	protected void updateFrame(final Runnable runnable) {

		final Lock lock = new ReentrantLock();
		lock.lock();
		final Condition condition = lock.newCondition();

		// invokeLater + Condition => synchroneous call
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lock.lock();
				getFrame().removeAll();
				runnable.run();
				getFrame().pack();
				condition.signal();
				lock.unlock();
			}
		});

		try {
			condition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.unlock();
	}
}
