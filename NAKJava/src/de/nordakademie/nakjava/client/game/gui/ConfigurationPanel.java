package de.nordakademie.nakjava.client.game.gui;

import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.component.ComboBox;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectWinStrategy;

public class ConfigurationPanel extends JPanel {

	private ComboBox cardDecks;
	private ComboBox winStrategies;

	public ConfigurationPanel() {
		cardDecks = new ComboBox(SelectDeckAction.class);
		winStrategies = new ComboBox(SelectWinStrategy.class);

		add(winStrategies);
		add(cardDecks);
	}

	public void setCurrentCardDeck(String cardDeck) {
		cardDecks.setSelectedItem(cardDeck);
	}

	public void setCurrentWinStrategy(String winStrategy) {
		winStrategies.setSelectedItem(winStrategy);
	}
}
