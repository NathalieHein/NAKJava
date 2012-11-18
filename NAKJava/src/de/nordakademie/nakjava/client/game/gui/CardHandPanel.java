package de.nordakademie.nakjava.client.game.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.client.internal.gui.ValueHolder;
import de.nordakademie.nakjava.client.internal.gui.component.AbstractGUIPanel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.server.internal.VisibleModelField;

public class CardHandPanel extends AbstractGUIPanel implements ValueHolder {

	private Map<String, CardInteractionPanel> cards;
	private VisibleModelField<List<CardInformation>> cardField;

	private boolean showButtons;

	public CardHandPanel(VisibleModelField<List<CardInformation>> cardField) {
		this(cardField, true);
	}

	public CardHandPanel(VisibleModelField<List<CardInformation>> cardField,
			boolean showButtons) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
		setMaximumSize(new Dimension(getMaximumSize().width,
				CardInformationPanel.CARD_HEIGHT + 2));
		this.cardField = cardField;
		cards = new HashMap<>();
		this.showButtons = showButtons;
	}

	/**
	 * Sets cards to visualize. It <b>is</b> possible to have a card more than
	 * once.
	 * 
	 * @param cards
	 */
	public void setCards(List<CardInformation> cards) {

		removeAll();

		for (CardInformation newCard : cards) {
			CardInteractionPanel cardPanel = new CardInteractionPanel(newCard,
					showButtons);
			this.cards.put(newCard.getTitle(), cardPanel);
			this.add(cardPanel);
		}

		this.revalidate();
	}

	@Override
	public void pickValue(Map<String, Object> genericValues) {
		setCards(cardField.getValue(genericValues));
	}
}
