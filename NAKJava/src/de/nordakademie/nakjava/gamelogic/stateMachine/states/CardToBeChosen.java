package de.nordakademie.nakjava.gamelogic.stateMachine.states;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.State;
import de.nordakademie.nakjava.gamelogic.stateMachine.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachine.events.EpsilonEvent;

public class CardToBeChosen extends State {
	protected CardToBeChosen(Map<Target, PlayerState> playerMap,
			WinStrategy strategy) {
		super(playerMap, strategy);
	}

	public State performTransition(EpsilonEvent event) {
		// TODO chooseCard
		// hier nur neuen State setzen - keinen Übergang, weil ausglöst durch
		// Action.perform()
		return new CardActionState(playerMap, strategy);
	}
}
