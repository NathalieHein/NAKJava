package de.nordakademie.nakjava.client.bot.genericbot;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.internal.gui.GUI;

public class GenericBotWithGUI {
	public static void main(String[] args) {
		try {
			new GenericBot(19, new GUI(false), true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
