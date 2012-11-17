package de.nordakademie.nakjava.client.game.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.ComboBox;
import de.nordakademie.nakjava.client.internal.gui.component.Label;
import de.nordakademie.nakjava.client.internal.gui.component.StatePanel;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.internal.VisibleModelField.ClientFieldTransformer;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.CreateNewDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.EditDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.FinishConfiguringAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.settingupgame.SelectWinStrategy;
import de.nordakademie.nakjava.util.StringUtilities;

public class ConfigurationPanel extends StatePanel {

	private ComboBox cardDecks;
	private ComboBox winStrategies;

	public ConfigurationPanel(Boolean actor) {
		super(actor);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel opponentPanel = new JPanel();
		JPanel gameTypePanel = new JPanel();
		JPanel selfPanel = new JPanel();

		add(opponentPanel);
		add(gameTypePanel);
		add(selfPanel);

		cardDecks = new ComboBox(SelectDeckAction.class,
				VisibleModelFields.CONFIGUREGAMESPECIFICMODEL_CHOSENDECK_SELF,
				actor);
		winStrategies = new ComboBox(SelectWinStrategy.class,
				VisibleModelFields.MODEL_STRATEGY_SELF, actor);

		opponentPanel
				.add(new Label(
						"",
						VisibleModelFields.PLAYERSTATE_NAME_OPPONENT
								.setTransformer(new ClientFieldTransformer<String, String>() {

									@Override
									public String transform(String value) {
										if (StringUtilities
												.isNotNullOrEmpty(value)) {
											return value;
										} else {
											return "Kein Gegner";
										}
									}
								}), ""));
		opponentPanel.add(new Label("(",
				VisibleModelFields.PLAYERSTATE_STATE_OPPONENT, ")"));

		gameTypePanel.add(new JLabel("Spielart:"));
		gameTypePanel.add(winStrategies);

		selfPanel.add(new JLabel("Deck:"));
		selfPanel.add(cardDecks);
		if (actor) {
			selfPanel.add(new Button("Deckauswal editieren",
					EditDeckAction.class));
			selfPanel.add(new Button("Neues Deck", CreateNewDeckAction.class));
			selfPanel.add(new Button("Bereit zum Spielen",
					FinishConfiguringAction.class));
		}

	}

	public void setCurrentCardDeck(String cardDeck) {
		cardDecks.setSelectedItem(cardDeck);
	}

	public void setCurrentWinStrategy(String winStrategy) {
		winStrategies.setSelectedItem(winStrategy);
	}

	@Override
	public State[] getStates() {
		return new State[] { State.CONFIGUREGAME, State.READYTOSTARTSTATE };
	}
}
