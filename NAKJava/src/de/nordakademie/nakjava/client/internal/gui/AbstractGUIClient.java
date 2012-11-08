package de.nordakademie.nakjava.client.internal.gui;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.nordakademie.nakjava.client.internal.Client;
import de.nordakademie.nakjava.client.shared.ClientAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public abstract class AbstractGUIClient extends Client {

	protected ActionContextDelegator delegator;
	protected final JFrame frame;

	protected AbstractGUIClient() throws RemoteException {
		super();
		frame = new JFrame();
		delegator = ActionContextDelegator.getInstance();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void stateChanged(PlayerState state) throws RemoteException {
		playerActionsChanged(state.getActions());
		playerModelChanged(state.getModel());
	}

	public void playerActionsChanged(List<ActionContext> actions) {
		extendActionContext(actions);
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

	protected void updateFrame(Runnable runnable) {
		frame.removeAll();
		SwingUtilities.invokeLater(runnable);
	}
}
