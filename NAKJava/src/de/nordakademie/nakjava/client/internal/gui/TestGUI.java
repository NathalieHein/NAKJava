package de.nordakademie.nakjava.client.internal.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

import de.nordakademie.nakjava.client.internal.Client;
import de.nordakademie.nakjava.server.shared.proxy.actions.KeyAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class TestGUI extends Client {

	private TextField textField;
	private Lock actionsLock;
	private List<ActionContext> actions;

	protected TestGUI() throws RemoteException {
		super();

		actionsLock = new ReentrantLock(true);

		JFrame frame = new JFrame("Unsere tolle Testanwendung");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textField = new TextField(15);

		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				ActionContext action = selectAction(e
						.getExtendedKeyCodeForChar(e.getKeyChar()));
				if (action != null) {
					textField.setText("");
					try {
						action.perform();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		frame.getContentPane().add(textField, BorderLayout.CENTER);
		frame.setSize(new Dimension(640, 480));
		frame.setVisible(true);

	}

	private ActionContext selectAction(int key) {
		actionsLock.lock();
		if (actions == null) {
			actionsLock.unlock();
			return null;
		}

		for (ActionContext action : actions) {
			if (action instanceof KeyAction) {
				KeyAction keyAction = (KeyAction) action;
				if (keyAction.getKey() == key) {
					actionsLock.unlock();
					return keyAction;
				}
			}

		}
		actionsLock.unlock();
		return null;
	}

	@Override
	public void stateChanged(PlayerState state) throws RemoteException {
		// TODO muss noch mal... :-)
		if (textField != null) {
			textField.setText(state.getModel().getName());
		}

		if (actionsLock != null) {
			actionsLock.lock();
		}

		actions = state.getActions();

		if (actionsLock != null) {
			actionsLock.unlock();
		}

	}

	public static void main(String[] args) {
		try {
			new TestGUI();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
