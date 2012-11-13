package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.client.internal.gui.component.TextField;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.DiscardDeckEditAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SaveDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.SelectCardForDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.editdeck.TypeDeckNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class CardSelectionPanel extends Panel {

	private JPanel cardOverview;
	private JPanel controlPanel;

	public CardSelectionPanel() {
		setLayout(new BorderLayout());
		cardOverview = new JPanel();
		cardOverview.setLayout(new GridLayout(0, 6));
		// cardOverview.setMinimumSize(new Dimension(1024, 600));
		// cardOverview.setPreferredSize(new Dimension(800, 600));
		// cardOverview.setMaximumSize(new Dimension(1024, 5000));
		JScrollPane scrollPane = new JScrollPane(cardOverview);

		// setMinimumSize(new Dimension(new Dimension(800, 600)));
		// setPreferredSize(new Dimension(new Dimension(800, 600)));
		// setMaximumSize(new Dimension(new Dimension(800, 600)));

		add(scrollPane, BorderLayout.CENTER);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		controlPanel.add(new JLabel("Name:"));
		controlPanel
				.add(new TextField(
						TypeDeckNameAction.class,
						VisibleModelFields.EDITDECKSPECIFICMODEL_CURRENTPARTOFDECKNAME_SELF));
		controlPanel.add(new Button("Speichern", SaveDeckAction.class));
		controlPanel.add(new Button("Abbrechen", DiscardDeckEditAction.class));

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
		cardOverview.removeAll();

		for (CardInformation card : cards.keySet()) {
			cardOverview.add(new CardSelector(card, cards.get(card)));
		}

		this.revalidate();
	}

	private class CardSelector extends JPanel {

		private CardSelector(final CardInformation card, boolean selected) {
			this.setLayout(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 0;

			add(new CardInformationPanel(card), constraints);
			Button button = new Button(selected ? "abwählen" : "auswählen",
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
			if (selected) {
				button.setBackground(Color.green);
			} else {
				button.setBackground(Color.gray);
			}

			constraints.gridy = 1;

			add(button, constraints);

		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CardSelectionPanel instance = new CardSelectionPanel();
		frame.add(instance);
		Map<CardInformation, Boolean> cards = new HashMap<>();
		for (int i = 0; i < 180; i++) {
			cards.put(new CardInformation(i + "", i + i + "", i + i + i + "",
					CardType.SPEZIAL), i % 2 == 0);
		}

		instance.setCards(cards);
		frame.setVisible(true);
		frame.pack();
	}

}
