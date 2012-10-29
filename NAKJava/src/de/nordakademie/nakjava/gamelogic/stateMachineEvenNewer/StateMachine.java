package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

public class StateMachine {

	// Suppresses default constructor for noninstantiability
	private StateMachine() {
		throw new AssertionError();
	}

	// TODO can the statemachine work without getting the model but other parts,
	// otherwise needs to be an object and needs to pass around reference to
	// itself
	public static void run(Map<Target, PlayerState> playerStateMap) {
		State state = playerStateMap.get(Target.SELF).getState()
				.getFollowUpState();
		playerStateMap.get(Target.SELF).setState(state);
		state.perform(playerStateMap);
	}

	public static void main(String[] args) {
		// TODO convert to JUnit-test
		PlayerState ps1 = new PlayerState(null);
		PlayerState ps2 = new PlayerState(null);
		List<String> cards = new ArrayList<>();
		for (int i = 1; i < 11; i++) {
			cards.add("Card" + i);
		}
		ps1.getCards().addAllCards(cards);
		ps1.setState(State.STOP);
		ps2.getCards().addAllCards(cards);
		ps2.setState(State.NEXT);
		ps1.getCards().drawUntilNCardsOnHand(7);
		ps2.getCards().drawUntilNCardsOnHand(5);
		Map<Target, PlayerState> playerStateMap = new HashMap<>();
		playerStateMap.put(Target.SELF, ps2);
		playerStateMap.put(Target.OPPONENT, ps1);
		do {
			StateMachine.run(playerStateMap);
			System.out.println("State: "
					+ playerStateMap.get(Target.SELF).getState());
			System.out.print("Cards:   ");
			for (String string : playerStateMap.get(Target.SELF).getCards()
					.getCardsOnHand()) {
				System.out.print(string + " ,");
			}
			System.out.println();
		} while (playerStateMap.get(Target.SELF).getState() != State.NEXT);

	}
}
