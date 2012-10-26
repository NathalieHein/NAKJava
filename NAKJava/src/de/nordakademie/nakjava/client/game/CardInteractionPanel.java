package de.nordakademie.nakjava.client.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.server.shared.proxy.actions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.SimulateCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class CardInteractionPanel extends JPanel {

	static final int CARD_WIDTH = 120;
	static final int CARD_HEIGHT = 240;

	private class CardInformationPanel extends JPanel {
		private CardInformationPanel(CardInformation card) {
			setLayout(new BorderLayout());
			setBackground(Color.cyan);
			add(new JLabel("<html><b>" + card.getTitle() + "</b></html>",
					JLabel.CENTER), BorderLayout.NORTH);
			add(new JLabel("<html><span style='font-weight:normal;'>"
					+ card.getInformation() + "</span></html>"),
					BorderLayout.CENTER);
			add(new JLabel("<html>" + card.getCost() + "</html>"),
					BorderLayout.SOUTH);
			setBorder(BorderFactory.createLineBorder(Color.black));
		}
	}

	public CardInteractionPanel(final CardInformation card) {
		setMaximumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		setLayout(new BorderLayout());
		add(new CardInformationPanel(card), BorderLayout.CENTER);
		JPanel buttons = new JPanel(new GridLayout(0, 1));
		add(buttons, BorderLayout.SOUTH);
		buttons.add(new Button("Spielen", new ActionContextSelector() {

			@Override
			public boolean select(ActionContext context) {
				if (context instanceof PlayCardAction) {
					return ((PlayCardAction) context).getCardName().equals(
							card.getTitle());
				} else {
					return false;
				}
			}
		}, true));
		buttons.add(new Button("Verwerfen", new ActionContextSelector() {

			@Override
			public boolean select(ActionContext context) {
				if (context instanceof WithdrawCardAction) {
					return ((WithdrawCardAction) context).getCardName().equals(
							card.getTitle());
				} else {
					return false;
				}
			}
		}, true));
		buttons.add(new Button("Simulieren", new ActionContextSelector() {

			@Override
			public boolean select(ActionContext context) {
				if (context instanceof SimulateCardAction) {
					return ((SimulateCardAction) context).getCardName().equals(
							card.getTitle());
				} else {
					return false;
				}
			}
		}, true));

	}
}
