package de.nordakademie.nakjava.gamelogic.stateMachine.states;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.State;
import de.nordakademie.nakjava.gamelogic.stateMachine.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachine.events.DiscardCardEvent;
import de.nordakademie.nakjava.gamelogic.stateMachine.events.PlayCardEvent;

public class CardActionState extends State {

	protected CardActionState(Map<Target, PlayerState> playerMap,
			WinStrategy strategy) {
		super(playerMap, strategy);
	}

	public State performTransition(PlayCardEvent event) {
		// TODO can I assume that cardAction was evoked by correct player whose
		// turn it is??
		return new PostActionState(playerMap, strategy).performTransition(event
				.perform(playerMap));
	}

	public State performTransition(DiscardCardEvent event) {
		return new PostActionState(playerMap, strategy)
				.performTransition(event);
	}
}
