package de.nordakademie.nakjava.gamelogic.stateMachine.states;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.State;
import de.nordakademie.nakjava.gamelogic.stateMachine.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachine.events.DiscardCardEvent;

public class PostActionState extends State {
	protected PostActionState(Map<Target, PlayerState> playerMap,
			WinStrategy strategy) {
		super(playerMap, strategy);
	}

	public State performTransition(DiscardCardEvent event) {
		return new EndOfRound(playerMap, strategy).performTransition(event
				.perform(playerMap, strategy));
	}
}
