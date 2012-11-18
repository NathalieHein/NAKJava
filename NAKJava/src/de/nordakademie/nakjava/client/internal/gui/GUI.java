package de.nordakademie.nakjava.client.internal.gui;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.nordakademie.nakjava.client.internal.gui.component.Frame;
import de.nordakademie.nakjava.client.internal.gui.component.StatePanel;
import de.nordakademie.nakjava.client.shared.ClientAction;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

/**
 * A GUI is the representation of the current game. A Gui can be attached to a
 * client. It could be used for passing actions to the server but it needent, a
 * bot could also do so.
 * 
 */
public class GUI implements GUIHook {

	private Frame frame;

	private ActionContextDelegator delegator;
	private PanelPicker panelPicker;

	private boolean actor;

	public GUI(boolean actor) {
		this.actor = actor;
	}

	/**
	 * After a new states arrives a we will switch to the AWT event Thread.
	 */
	@Override
	public void newState(final PlayerState state) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					playerModelChanged(state.getModel());
					playerActionsChanged(state.getActions(), state.getBatch());
					getFrame().pack();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void playerActionsChanged(List<ActionContext> actions, long batch) {
		extendActionContext(actions, batch);
		delegator.delegateActionContexts(actions, batch, true);
	}

	@Override
	public void preCheckin() {
		frame = new Frame();
		frame.setResizable(false);
		delegator = ActionContextDelegator.getInstance();
		panelPicker = new PanelPicker(actor);

		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
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
					GUI.this.revokeActionContexts(batch);
				}
			});
		}
	}

	/**
	 * Chooses the a registered panel for the passed model The panel will
	 * automatically attached if it changes.
	 * 
	 * @param model
	 */
	public void playerModelChanged(PlayerModel model) {
		final StatePanel statePanel = panelPicker
				.pickPanel(VisibleModelFields.PLAYERSTATE_STATE_SELF
						.getValue(model.getGenericTransfer()));

		if (statePanel != null) {
			updateFrame(new Runnable() {

				@Override
				public void run() {
					getFrame().add(statePanel);
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

		getFrame().getContentPane().removeAll();
		runnable.run();
		for (Component comp : getFrame().getComponents()) {
			comp.revalidate();
		}

	}

	@Override
	public void error(String text) {
		JOptionPane.showMessageDialog(getFrame(), text, "Fehler",
				JOptionPane.ERROR_MESSAGE);
	}

}
