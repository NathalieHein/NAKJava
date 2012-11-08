package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.AdjustCardhand;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.Postaction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.Preaction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.ReadyToStartAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.RoundEndAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.StateAction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.internal.model.Model;

public class StateMachine {
	static StateMachine instance;
	Map<State, StateAction> stateToAction;

	// TODO can the statemachine work without getting the model but other parts,
	// otherwise needs to be an object and needs to pass around reference to
	// itself
	private StateMachine() {
		initialize();
	}

	public static synchronized StateMachine getInstance() {
		if (instance == null) {
			instance = new StateMachine();
		}
		return instance;
	}

	private void initialize() {
		stateToAction.put(State.PREACTIONSTATE, new Preaction());
		stateToAction.put(State.POSTACTIONSTATE, new Postaction());
		stateToAction.put(State.ADJUSTCARDHANDSTATE, new AdjustCardhand());
		stateToAction.put(State.STOP, new RoundEndAction());
		stateToAction.put(State.READYTOSTARTSTATE, new ReadyToStartAction());
	}

	public void run(Model model) {
		State nextState = model.getSelf().getState().getFollowUpState();
		model.getSelf().setState(nextState);
		if (stateToAction.containsKey(nextState)) {
			stateToAction.get(nextState).perform(model);
		}
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
