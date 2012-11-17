package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import de.nordakademie.nakjava.client.internal.gui.component.AbstractGUIPanel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

public class CardInformationPanel extends AbstractGUIPanel {

	public static final int CARD_WIDTH = 160;
	public static final int CARD_HEIGHT = 240;

	public CardInformationPanel(CardInformation card) {
		setLayout(new BorderLayout());
		setBackground(Color.cyan);
		add(new JLabel("<html><b>" + card.getTitle() + "</b></html>",
				JLabel.CENTER), BorderLayout.NORTH);
		add(new JLabel("<html><span style='font-weight:normal;'>"
				+ card.getInformation().replaceAll("\n", "<br/>")
				+ "</span></html>"), BorderLayout.CENTER);
		add(new JLabel("<html>" + card.getCost().replaceAll("\n", "<br/>")
				+ "</html>"), BorderLayout.SOUTH);
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.setMaximumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		this.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		this.setMinimumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
	}
}
