package de.nordakademie.nakjava.gamelogic.stateMachine.states;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.State;
import de.nordakademie.nakjava.gamelogic.stateMachine.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachine.events.ChangeRoundEvent;
import de.nordakademie.nakjava.gamelogic.stateMachine.events.GameOverEvent;

public class EndOfRound extends State {
	protected EndOfRound(Map<Target, PlayerState> playerMap,
			WinStrategy strategy) {
		super(playerMap, strategy);
		// TODO Auto-generated constructor stub
	}

	public State performTransition(ChangeRoundEvent event) {
		return new PreActionState(playerMap, strategy).performTransition(event
				.perform(playerMap));
	}

	public State performTransition(GameOverEvent event) {
		// inform about end of game
		return new GameIsOver(playerMap, strategy);
	}
}
