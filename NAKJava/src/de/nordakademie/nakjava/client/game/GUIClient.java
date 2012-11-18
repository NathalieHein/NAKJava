package de.nordakademie.nakjava.client.game;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.internal.AbstractClient;
import de.nordakademie.nakjava.client.internal.gui.GUI;

/**
 * Starter for a Client with GUI
 * 
 */
public class GUIClient extends AbstractClient {

	protected GUIClient() throws RemoteException {
		super(new GUI(true));
	}

	public static void main(String[] args) {
		try {
			new GUIClient();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
