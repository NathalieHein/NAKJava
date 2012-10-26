package de.nordakademie.nakjava.gamelogic.stateMachine.states;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.State;
import de.nordakademie.nakjava.gamelogic.stateMachine.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachine.events.EpsilonEvent;

public class PreActionState extends State {

	// TODO this is sooooo ridiculous
	// TODO maybe StartState with public Constructor??
	public PreActionState(Map<Target, PlayerState> playerMap,
			WinStrategy strategy) {
		super(playerMap, strategy);
	}

	public State performTransition(EpsilonEvent event) {
		// TODO receiveRessources
		return new CardToBeChosen(playerMap, strategy).performTransition(event);
	}

}
