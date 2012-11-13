package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.SimulateCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class CardInteractionPanel extends JPanel {

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
