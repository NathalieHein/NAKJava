package de.nordakademie.nakjava.client.bot.geneticBot;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import de.nordakademie.nakjava.client.internal.gui.GUI;

/**
 * GUI starter for genetic bot
 * 
 * @author Kai
 * 
 */
public class GeneticBotWithGUI {
	public static void main(String[] args) {
		try {
			new GeneticBot(new GUI(false), true);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
