package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

public class CardInformationPanel extends JPanel {

	public static final int CARD_WIDTH = 160;
	public static final int CARD_HEIGHT = 240;

	public CardInformationPanel(CardInformation card) {
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
		this.setMaximumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		this.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		this.setMinimumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
	}
}
