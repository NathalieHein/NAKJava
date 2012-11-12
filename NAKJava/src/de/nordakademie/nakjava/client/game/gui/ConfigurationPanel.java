package de.nordakademie.nakjava.client.game.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.ComboBox;
import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.CreateNewDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.EditDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectWinStrategy;

public class ConfigurationPanel extends Panel {

	private ComboBox cardDecks;
	private ComboBox winStrategies;

	public ConfigurationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel opponentPanel = new JPanel();
		JPanel gameTypePanel = new JPanel();
		JPanel selfPanel = new JPanel();

		add(opponentPanel);
		add(gameTypePanel);
		add(selfPanel);

		cardDecks = new ComboBox(SelectDeckAction.class);
		winStrategies = new ComboBox(SelectWinStrategy.class);

		gameTypePanel.add(new JLabel("Spielart:"));
		gameTypePanel.add(winStrategies);

		selfPanel.add(new JLabel("Deck:"));
		selfPanel.add(cardDecks);
		selfPanel.add(new Button("Deckauswal editieren", EditDeckAction.class));
		selfPanel.add(new Button("Neues Deck", CreateNewDeckAction.class));
	}

	public void setCurrentCardDeck(String cardDeck) {
		cardDecks.setSelectedItem(cardDeck);
	}

	public void setCurrentWinStrategy(String winStrategy) {
		winStrategies.setSelectedItem(winStrategy);
	}

	@Override
	public State[] getStates() {
		return new State[] { State.CONFIGUREGAME };
	}
}
