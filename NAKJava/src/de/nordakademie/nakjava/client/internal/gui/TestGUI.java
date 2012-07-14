package de.nordakademie.nakjava.client.internal.gui;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

import de.nordakademie.nakjava.client.internal.Client;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;

public class TestGUI extends Client {

	private Lock actionsLock;
	private List<ActionContext> actions;

	protected TestGUI() throws RemoteException {
		super();

		actionsLock = new ReentrantLock(true);

		JFrame frame = new JFrame("Unsere tolle Testanwendung");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(new Dimension(640, 480));
		frame.setVisible(true);

	}

	@Override
	public void stateChanged(PlayerState state) throws RemoteException {
		// TODO muss noch mal... :-)

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
