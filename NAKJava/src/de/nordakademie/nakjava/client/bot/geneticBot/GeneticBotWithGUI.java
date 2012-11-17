package de.nordakademie.nakjava.client.bot.geneticBot;

import java.rmi.RemoteException;

import de.nordakademie.nakjava.client.internal.gui.GUI;

public class GeneticBotWithGUI {
	public static void main(String[] args) {
		try {
			new GeneticBot(new GUI(false), true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
