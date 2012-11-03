package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.client.internal.gui.component.CheckBox;
import de.nordakademie.nakjava.client.internal.gui.component.TextField;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.server.shared.proxy.actions.DiscardDeckEditAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.SaveDeckAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.TypeDeckNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class CardSelectionPanel extends JPanel {

	private JPanel cardOverview;
	private JPanel controlPanel;

	public CardSelectionPanel() {
		setLayout(new BorderLayout());
		cardOverview = new JPanel();
		cardOverview.setLayout(new GridLayout(0, 6));
		JScrollPane scrollPane = new JScrollPane(cardOverview);
		add(scrollPane, BorderLayout.CENTER);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		controlPanel.add(new JLabel("Name:"));
		controlPanel.add(new TextField(TypeDeckNameAction.class));
		controlPanel.add(new Button("Speichern", SaveDeckAction.class));
		controlPanel.add(new Button("Abbrechen", DiscardDeckEditAction.class));

		add(controlPanel, BorderLayout.SOUTH);

	}

	public void setCards(List<CardInformation> cards) {
		cardOverview.removeAll();

		for (CardInformation card : cards) {
			cardOverview.add(new CardSelector(card));
		}

		this.revalidate();
	}

	private class CardSelector extends JPanel {

		private CardSelector(CardInformation card) {
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.setToolTipText("<html>" + card.getInformation() + "<br/>"
					+ card.getCost() + "</html>");
			add(new JLabel(card.getTitle()));
			add(new CheckBox(new ActionContextSelector() {

				@Override
				public boolean select(ActionContext context) {
					// TODO Auto-generated method stub
					return false;
				}
			}));
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CardSelectionPanel instance = new CardSelectionPanel();
		frame.add(instance);
		List<CardInformation> cards = new LinkedList<>();
		for (int i = 0; i < 180; i++) {
			cards.add(new CardInformation(i + "", i + i + "", i + i + i + "",
					CardType.SPEZIAL));
		}

		instance.setCards(cards);

	}

}
