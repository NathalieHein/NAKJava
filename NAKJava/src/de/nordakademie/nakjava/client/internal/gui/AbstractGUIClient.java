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

	private Lock updatePanelLock;
	private Condition updatePanelCondition;

	protected AbstractGUIClient() throws RemoteException {
		super();
		updatePanelLock = new ReentrantLock();
		updatePanelCondition = updatePanelLock.newCondition();
	}

	@Override
	public void stateChange(PlayerState state) {
		playerModelChanged(state.getModel());
		playerActionsChanged(state.getActions(), state.getBatch());
		getFrame().pack();
	}

	private void playerActionsChanged(List<ActionContext> actions, long batch) {
		extendActionContext(actions, batch);
		delegator.delegateActionContexts(actions, batch, true);
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
	private void extendActionContext(List<ActionContext> actions,
			final long batch) {
		for (final ActionContext actionContext : actions) {
			actionContext.addPreClientAction(new ClientAction() {
				@Override
				public void perform() {
					AbstractGUIClient.this.revokeActionContexts(batch);
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
	}

	public void revokeActionContexts(long batch) {
		delegator.revokeActionContexts(batch);
	}

	/**
	 * Synchronous call for updating a panel. Changing a Panel needs to switch
	 * to the AWT-Event thread, which can only be done by invokeLater. By using
	 * a lock, invoke-later is made synchronous. We can not use invokeAndWait,
	 * because a button might be pressed and needs to be released. With invoke
	 * later we ensure to invoke that action after the button is released :-).
	 * 
	 * @param runnable
	 */
	private void updateFrame(final Runnable runnable) {

		updatePanelLock.lock();

		// invokeLater + Condition => synchronous call
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				updatePanelLock.lock();
				getFrame().getContentPane().removeAll();
				runnable.run();
				for (Component comp : getFrame().getComponents()) {
					comp.revalidate();
				}
				updatePanelCondition.signal();
				updatePanelLock.unlock();
			}
		});

		try {
			updatePanelCondition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			updatePanelLock.unlock();
		}

	}
}
