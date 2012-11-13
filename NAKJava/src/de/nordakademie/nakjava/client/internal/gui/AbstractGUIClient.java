package de.nordakademie.nakjava.client.internal.gui;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.nordakademie.nakjava.client.internal.Client;
import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.client.shared.ClientAction;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public abstract class AbstractGUIClient extends Client {

	protected ActionContextDelegator delegator;
	protected PanelPicker panelPicker;

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
		frame.setResizable(false);
		delegator = ActionContextDelegator.getInstance();
		panelPicker = new PanelPicker();
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

	public void playerModelChanged(PlayerModel model) {
		final Panel panel = panelPicker
				.pickPanel(VisibleModelFields.PLAYERSTATE_STATE_SELF
						.getValue(model.getGenericTransfer()));

		if (panel != null) {
			updateFrame(new Runnable() {

				@Override
				public void run() {
					getFrame().add(panel);
				}
			});
		}

		panelPicker.getCurrentPanel().setModel(model);
		getFrame().pack();

	}

	public void revokeActionContexts(long batch) {
		delegator.revokeActionContexts(batch);
	}

	private void updateFrame(final Runnable runnable) {

		final Lock lock = new ReentrantLock();
		lock.lock();
		final Condition condition = lock.newCondition();

		// invokeLater + Condition => synchronous call
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lock.lock();
				getFrame().getContentPane().removeAll();
				runnable.run();
				for (Component comp : getFrame().getComponents()) {
					comp.revalidate();
				}
				getFrame().pack();
				condition.signal();
				lock.unlock();
			}
		});

		try {
			condition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}
}
