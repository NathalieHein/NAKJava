package de.nordakademie.nakjava.gamelogic.stateMachine.events;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.Event;
import de.nordakademie.nakjava.gamelogic.stateMachine.WinStrategy;

public class DiscardCardEvent implements Event {
	private String card;

	public DiscardCardEvent(String card) {
		this.card = card;
	}

	protected String getCard() {
		return card;
	}

	public Event perform(Map<Target, PlayerState> map, WinStrategy strategy) {
		// TODO noch auslagern in eigene Methoden
		PlayerState current = map.get(Target.SELF);

		if (!current.getCards().discardCardFromHand(card)) {
			// TODO noch ordentliche Fehlerbehandlung machen
			System.out.println("da läuft wohl was schief:(");
		}

		current.getCards().drawUntilNCardsOnHand(6);

		if (!strategy.hasWon(map)) {
			return new ChangeRoundEvent();
		} else {
			return new GameOverEvent();
		}
	}
}
