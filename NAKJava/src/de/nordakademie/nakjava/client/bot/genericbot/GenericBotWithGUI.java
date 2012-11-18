package de.nordakademie.nakjava.client.bot.genericbot;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import de.nordakademie.nakjava.client.internal.gui.GUI;

/**
 * Starter for a GenericBot with gui.
 * 
 */
public class GenericBotWithGUI {
	public static void main(String[] args) {
		try {
			new GenericBot(10, new GUI(false), true);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
