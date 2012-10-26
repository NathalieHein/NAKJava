package de.nordakademie.nakjava.gamelogic.stateMachine.events;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.AbstractCard;
import de.nordakademie.nakjava.gamelogic.cards.impl.CardLibrary;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.Event;

public class PlayCardEvent implements Event {
	private String cardName;

	public PlayCardEvent(String cardName) {
		this.cardName = cardName;
	}

	public Event perform(Map<Target, PlayerState> map) {
		// TODO assuming that card was found in cardLibrary -> is that alright??
		AbstractCard card = getCard(map);
		card.payImpl(map);
		card.performActionImpl(map);
		return new DiscardCardEvent(cardName);
	}

	public AbstractCard getCard(Map<Target, PlayerState> map) {
		if (CardLibrary.get().getCards().containsKey(cardName)) {
			return CardLibrary.get().getCards().get(cardName);
		}
		return null;
	}

}
