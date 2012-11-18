package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.component.AbstractGUIPanel;
import de.nordakademie.nakjava.client.internal.gui.component.Button;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.PlayCardAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.cardActions.WithdrawCardAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

/**
 * Adds Game interactions for {@link CardInformationPanel}s. Those can be turned
 * on and off.
 * 
 * @author Kai
 * 
 */
public class CardInteractionPanel extends AbstractGUIPanel {

	/**
	 * Wraps a cardinformation into a panel with game buttons for play and
	 * withdraw.
	 * 
	 * @param card
	 *            card to visualize
	 * @param actor
	 *            should buttons be shown
	 */
	public CardInteractionPanel(final CardInformation card, boolean actor) {
		super();
		setMaximumSize(new Dimension(CardInformationPanel.CARD_WIDTH,
				CardInformationPanel.CARD_HEIGHT));
		setPreferredSize(new Dimension(CardInformationPanel.CARD_WIDTH,
				CardInformationPanel.CARD_HEIGHT));
		setLayout(new BorderLayout());
		add(new CardInformationPanel(card), BorderLayout.CENTER);

		if (actor) {
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
						return ((WithdrawCardAction) context).getCardName()
								.equals(card.getTitle());
					} else {
						return false;
					}
				}
			}, true));
		}

	}
}
