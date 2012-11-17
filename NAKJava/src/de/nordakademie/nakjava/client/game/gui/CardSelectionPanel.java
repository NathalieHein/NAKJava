package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.StatePanel;
import de.nordakademie.nakjava.client.internal.gui.component.TextField;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.DiscardDeckEditAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SaveDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SelectCardForDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.TypeDeckNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class CardSelectionPanel extends StatePanel {

	private JPanel cardOverview;
	private JPanel controlPanel;

	private Set<CardInformation> oldCardInformation;
	private Map<CardInformation, CardSelector> cache;

	public CardSelectionPanel(Boolean actor) {
		super(actor);
		setLayout(new BorderLayout());
		cache = new HashMap<>();
		cardOverview = new JPanel();
		cardOverview.setLayout(new GridLayout(0, 6));

		JScrollPane scrollPane = new JScrollPane(cardOverview);
		scrollPane.setPreferredSize(new Dimension(
				CardInformationPanel.CARD_WIDTH * 6 + 20,
				CardInformationPanel.CARD_HEIGHT * 2 + 80));

		add(scrollPane, BorderLayout.CENTER);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		controlPanel.add(new JLabel("Name:"));
		controlPanel
				.add(new TextField(
						TypeDeckNameAction.class,
						VisibleModelFields.EDITDECKSPECIFICMODEL_CURRENTPARTOFDECKNAME_SELF,
						actor));
		if (actor) {
			controlPanel.add(new Button("Speichern", SaveDeckAction.class));
			controlPanel.add(new Button("Abbrechen",
					DiscardDeckEditAction.class));
		}

		add(controlPanel, BorderLayout.SOUTH);

	}

	@Override
	public State[] getStates() {
		return new State[] { State.EDITDECK };
	}

	@Override
	protected void setModelImpl(Map<String, Object> values) {
		setCards(VisibleModelFields.EDITDECKSPECIFICMODEL_CHOSENCARDS_SELF
				.getValue(values));
	}

	public void setCards(Map<CardInformation, Boolean> cards) {
		if (oldCardInformation != null) {
			if (oldCardInformation.equals(cards.keySet())) {
				for (CardInformation info : cards.keySet()) {
					cache.get(info).setSelected(cards.get(info));
				}
			}
		} else {
			cache = new HashMap<>();
			for (CardInformation card : cards.keySet()) {
				CardSelector selector = new CardSelector(card, cards.get(card));
				cardOverview.add(selector);
				cache.put(card, selector);
			}
		}

		oldCardInformation = cards.keySet();

		// this.revalidate();
	}

	private class CardSelector extends JPanel {

		private Button button;

		private CardSelector(final CardInformation card, boolean selected) {
			this.setLayout(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 0;

			add(new CardInformationPanel(card), constraints);
			button = new Button(selected ? "abwählen" : "auswählen",
					new ActionContextSelector() {

						@Override
						public boolean select(ActionContext context) {
							if (context instanceof SelectCardForDeckAction) {
								SelectCardForDeckAction action = (SelectCardForDeckAction) context;
								return action.getValue()
										.equals(card.getTitle());
							}

							return false;
						}
					}, true);
			setSelected(selected);

			constraints.gridy = 1;

			add(button, constraints);

		}

		public void setSelected(boolean enabled) {
			if (enabled) {
				button.setBackground(Color.green);
				button.setText("abwählen");
			} else {
				button.setBackground(Color.gray);
				button.setText("auswählen");
			}
		}
	}

}
